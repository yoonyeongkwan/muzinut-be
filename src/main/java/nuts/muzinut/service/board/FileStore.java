package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.AdminUploadFile;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.exception.NoUploadFileException;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.board.AdminUploadFileRepository;
import nuts.muzinut.repository.board.FreeBoardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<AdminUploadFile> storeFiles(List<MultipartFile> multipartFiles, AdminBoard adminBoard) throws IOException {
        List<AdminUploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile, adminBoard));
            }
        }
        return storeFileResult;
    }

    public AdminUploadFile storeFile(MultipartFile multipartFile, AdminBoard adminBoard) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        AdminUploadFile adminUploadFile = new AdminUploadFile(storeFilename, originalFilename);
        adminUploadFile.addFiles(adminBoard);
        uploadFileRepository.save(adminUploadFile);

        return adminUploadFile;
    }

    /**
     * 자유 게시판의 파일 및 엔티티 저장
     * @param multipartFile: react quill
     * @param freeBoard: 자유 게시판 엔티티
     * @return: freeBoard
     * @throws IOException: 파일 업로드 실패시 발생
     * @throws NoUploadFileException: 업로드할 파일이 없을 시 발생
     */
    public FreeBoard storeFile(MultipartFile multipartFile, FreeBoard freeBoard) throws IOException, NoUploadFileException {
        if (multipartFile.isEmpty()) {
            throw new NoUploadFileException("자유게시판의 업로드 파일이 존재하지 않음");
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename))); //파일 저장

        freeBoard.setFilename(storeFilename);
        return freeBoardRepository.save(freeBoard);
    }

    //id는 adminBoard pk
    public void deleteAdminAttachedFile(Long id) {
        List<AdminUploadFile> files = uploadFileRepository.getAdminUploadFile(id);
        if (files.isEmpty()) {
            return;
        }
        deleteFiles(files);
    }

    //첨부파일 업데이트
    public void updateAdminAttachedFile(Long id) {
        deleteAdminAttachedFile(id);
        uploadFileRepository.deleteByAdminBoardId(id);
    }

    //어드민 첨부파일을 삭제하는 메서드
    private void deleteFiles(List<AdminUploadFile> files) {
        for (AdminUploadFile f : files) {
            File file = new File(fileDir + f.getStoreFilename());
            file.delete();
        }
    }

    //파일 이름을 랜덤으로 생성하는 메서드 (저장하는 파일 명이 겹치면 안되기 때문)
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //확장자 명을 추출하는 메서드 ex) a.txt -> txt 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }


}
