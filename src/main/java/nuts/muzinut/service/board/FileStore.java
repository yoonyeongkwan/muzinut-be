package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.controller.board.FileType;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.exception.NoUploadFileException;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.board.AdminUploadFileRepository;
import nuts.muzinut.repository.board.FreeBoardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static nuts.muzinut.controller.board.FileType.*;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class FileStore {

    private final AdminBoardRepository adminBoardRepository;
    private final AdminUploadFileRepository uploadFileRepository;
    private final FreeBoardRepository freeBoardRepository;

    @Value("${spring.file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /**
     *
     * @param attachedFiles: 첨부 파일
     * @param adminBoard: 해당 어드민 게시판
     * @return: 첨부 파일의 원래 이름 & 저장된 이름을 리스트로 반환
     * @throws IOException
     */
    public AdminBoard storeFiles(List<MultipartFile> attachedFiles, AdminBoard adminBoard) throws IOException {
        List<Map<FileType, String>> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : attachedFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeAttachFile(multipartFile, adminBoard)); //어드민 게시판의 첨부파일을 하나씩 저장
            }
        }
        return setAttachFileName(storeFileResult, adminBoard);
    }

    //첨부 파일들을 셋팅 해주는 메서드
    public AdminBoard setAttachFileName(List<Map<FileType, String>> filenames, AdminBoard adminBoard) {
        for (Map<FileType, String> f : filenames) {
            AdminUploadFile adminUploadFile = new AdminUploadFile(f.get(STORE_FILENAME), f.get(ORIGIN_FILENAME));
            adminUploadFile.addFiles(adminBoard);
//            adminBoard.getAdminUploadFiles().
//                    add(new AdminUploadFile(f.get(STORE_FILENAME), f.get(ORIGIN_FILENAME)));
        }
        return adminBoard;
    }


    //어드민 게시판의 첨부 파일을 저장하는 메서드
    public Map<FileType, String> storeAttachFile(MultipartFile multipartFile, AdminBoard adminBoard) throws IOException {
        Map<FileType, String> filenames = new HashMap<>();
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename))); //파일 저장
        filenames.put(ORIGIN_FILENAME, originalFilename);
        filenames.put(STORE_FILENAME, storeFilename);

        return filenames;
    }

    public Map<FileType, String> storeFile(MultipartFile multipartFile) throws IOException {
        Map<FileType, String> filename = new HashMap<>();
        if (multipartFile.isEmpty()) {
            throw new NoUploadFileException("업로드 파일이 존재하지 않음");
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename))); //파일 저장
        filename.put(STORE_FILENAME, storeFilename);
        filename.put(ORIGIN_FILENAME, originalFilename);

        return filename;
    }

    /**
     * 어드민 게시판의 파일 저장
     * @param multipartFile: react quill
     * @param adminBoard: 어드민 게시판 엔티티
     * @return: adminBoard
     * @throws IOException: 파일 업로드 실패시 발생
     * @throws NoUploadFileException: 업로드할 파일이 없을 시 발생
     */
    public AdminBoard storeFile(MultipartFile multipartFile, AdminBoard adminBoard) throws IOException, NoUploadFileException {
        if (multipartFile.isEmpty()) {
            throw new NoUploadFileException("자유게시판의 업로드 파일이 존재하지 않음");
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename))); //파일 저장
        adminBoard.changeFilename(storeFilename); //파일 이름 셋팅

        return adminBoard;
    }

    /**
     * 기존에 있는 파일은 삭제하고 새로 받은 파일을 저장한다
     * @param multipartFile: react quill
     * @param deleteFilename: 삭제할 파일이름 (실제로 저장된 이름)
     * @throws IOException: 파일 업로드 실패시 발생
     * @throws NoUploadFileException: 업로드할 파일이 없을 시 발생
     * @return: 새롭게 저장한 파일 이름을 반환
     */
    public String updateFile(MultipartFile multipartFile, String deleteFilename) throws IOException, NoUploadFileException {
        if (multipartFile.isEmpty()) {
            throw new NoUploadFileException("자유게시판의 업로드 파일이 존재하지 않음");
        }

        deleteFile(deleteFilename); //기존 파일 삭제
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename))); //파일 저장

        return storeFilename;
    }

    //id는 adminBoard pk
    public void deleteAdminAttachedFile(AdminBoard adminBoard) {
        List<AdminUploadFile> files = adminBoard.getAdminUploadFiles();
        if (files.isEmpty()) {
            return;
        }
        deleteFiles(files); //첨부 파일 삭제
        deleteFile(adminBoard.getFilename()); //quill 파일 삭제
    }

    //첨부파일 업데이트
    public void updateAdminAttachedFile(AdminBoard adminBoard) {
        deleteAdminAttachedFile(adminBoard); //이전에 저장했던 파일들 삭제
        uploadFileRepository.deleteByAdminBoard(adminBoard); //기존의 첨부파일을 디비에서 삭제
    }

    //어드민 첨부파일을 삭제하는 메서드
    private void deleteFiles(List<AdminUploadFile> files) {
        for (AdminUploadFile f : files) {
            File file = new File(fileDir + f.getStoreFilename());
            file.delete();
        }
    }


    //파일을 삭제하는 메서드 (퀼 파일, 프로필 이미지 삭제할 때 사용)
    public void deleteFile(String filename) {
            File file = new File(fileDir + filename);
            file.delete();
    }

    //파일 이름을 랜덤으로 생성하는 메서드 (저장하는 파일 명이 겹치면 안되기 때문)
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //확장자 명을 추출하는 메서드 ex) a.txt -> txt 추출
    public String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    //프로필 이미지를 폼 데이터에 넣어주는 메서드
    public void setImageHeaderWithData(Set<String> profileImages, MultiValueMap<String, Object> formData) {

        List<String> imagesFullPath = profileImages.stream()
                .map(this::getFullPath)
                .toList();

        imagesFullPath.forEach(i -> formData.add("profileImg", new FileSystemResource(i)));
    }

    // 프로필 이미지와 배너 이미지를 폼 데이터에 넣어주는 메서드
    public void setProfileAndBannerImage(String profileImage, String bannerImage, MultiValueMap<String, Object> formData) {
        List<String> imagePaths = List.of(profileImage, bannerImage)
                .stream()
                .map(this::getFullPath)
                .toList();

        log.info("Setting profile image: " + profileImage);
        log.info("Setting banner image: " + bannerImage);

        formData.add("profileImage", new FileSystemResource(imagePaths.get(0)));
        formData.add("bannerImage", new FileSystemResource(imagePaths.get(1)));
    }

    // 앨범 이미지를 폼 데이터에 넣어주는 메서드
    public void setAlbumImages(String albumImgName, MultiValueMap<String, Object> formData, String key) {
        FileSystemResource albumImgResource = new FileSystemResource(fileDir + "/albumImg/" + albumImgName);
        log.info("Setting albumImage: " + albumImgResource);
        formData.add(key, albumImgResource);
    }
}
