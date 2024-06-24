package nuts.muzinut.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.AdminUploadFile;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.board.admin.AdminBoardsDto;
import nuts.muzinut.dto.board.admin.DetailAdminBoardDto;
import nuts.muzinut.dto.board.admin.AdminBoardForm;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.exception.BoardNotExistException;
import nuts.muzinut.exception.BoardNotFoundException;
import nuts.muzinut.exception.NotFoundFileException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.board.AdminUploadFileRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.board.AdminBoardService;
import nuts.muzinut.service.board.FileStore;
import nuts.muzinut.service.security.UserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
//@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBoardController {

    private final AdminBoardRepository adminBoardRepository;
    private final AdminUploadFileRepository uploadFileRepository;
    private final UserRepository userRepository;
    private final FileStore fileStore;
    private final AdminBoardService adminBoardService;
    private final UserService userService;

    @GetMapping("/admin-boards/new")
    public String newAdminBoard(@ModelAttribute AdminBoardForm form) {
        return "/board/admin-board-form";
    }

/*
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin-boards/new")
    public ResponseEntity<MessageDto> saveAdminBoard(@ModelAttribute AdminBoardForm form) throws IOException {

        UserDto admin = userService.getMyUserWithAuthorities();
        String nickname = admin.getNickname();
        String username = admin.getUsername();

        AdminBoard adminBoard = new AdminBoard(form.getTitle(), form.getContent());

        List<AdminUploadFile> adminUploadFiles = fileStore.storeFiles(form.getAttachFiles(), adminBoard); //첨부 파일들을 저장

        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/admin-boards/" + adminBoard.getId())); //생성한 게시판으로 리다이렉트

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .headers(header)
                .body(new MessageDto("어드민 게시판이 생성되었습니다"));
    }
*/

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin-boards/new")
    public ResponseEntity<MessageDto> saveAdminBoard(@ModelAttribute AdminBoardForm form) throws IOException {

        try {
            Optional<User> findUser = userService.getUserWithUsername(); //NotFoundMemberException 발생할 수 있음
            User user = findUser.get();
            String nickname = user.getNickname();
//            log.info("user: {}", user.getNickname());

            AdminBoard adminBoard = adminBoardService.createAdminBoard(
                    new AdminBoard(form.getTitle(), form.getContent()), nickname);

            List<AdminUploadFile> adminUploadFiles = fileStore.storeFiles(form.getAttachFiles(), adminBoard); //첨부 파일들을 저장

            HttpHeaders header = new HttpHeaders();
            header.setLocation(URI.create("/admin-boards/" + adminBoard.getId())); //생성한 게시판으로 리다이렉트

            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .headers(header)
                    .body(new MessageDto("어드민 게시판이 생성되었습니다"));

        } catch (NotFoundMemberException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new MessageDto("게시판에 접근할 수 없습니다."));
        }
    }

    /**
     * 특정 어드민 게시판을 조회
     * @param id: admin board pk
     * @throws: db로 부터 검색되는 엔티티가 없을 경우 NotFoundEntityException 예외 발생 (404 Not found)
     */
    @ResponseBody
    @GetMapping("/admin-boards/{id}")
    public DetailAdminBoardDto getAdminBoard(@PathVariable Long id) {
        DetailAdminBoardDto detailAdminBoard = adminBoardService.getDetailAdminBoard(id);
        if (detailAdminBoard == null) {
            throw new BoardNotFoundException("해당 게시판이 존재하지 않습니다");
        }
        return detailAdminBoard;
    }

    //특정 어드민 게시판 수정
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin-boards/{id}")
    public ResponseEntity<MessageDto> updateAdminBoard(@ModelAttribute AdminBoardForm form,
                                                       @PathVariable Long id) throws IOException {

        try {
            AdminBoard adminBoard = adminBoardService.getAdminBoard(id);
            fileStore.updateAdminAttachedFile(id); //기존에 저장해놓았던 첨부파일 업데이트
            List<AdminUploadFile> adminUploadFiles = fileStore.storeFiles(form.getAttachFiles(), adminBoard); //첨부 파일들을 저장

            HttpHeaders header = new HttpHeaders();
            header.setLocation(URI.create("/admin-boards/" + adminBoard.getId())); //수정한 게시판으로 리다이렉트

            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .headers(header)
                    .body(new MessageDto("어드민 게시판이 수정되었습니다"));

        } catch (BoardNotExistException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageDto("서버 오류로 어드민 게시판이 수정되질 않았습니다."));
        }
    }


    //어드민 게시판 삭제
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin-boards/{id}")
    public ResponseEntity<MessageDto> deleteAdminBoard(@PathVariable Long id) {

        fileStore.deleteAdminAttachedFile(id);
        adminBoardService.deleteAdminBoard(id);
        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/admin-boards?page=0")); //어드민 게시판으로 리다이렉트

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .headers(header)
                .body(new MessageDto("어드민 게시판이 삭제되었습니다"));

    }

    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    //공지 사항 게시판 첨부 파일 다운로드 (회원들만 가능)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/attach/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException {

        try {
            AdminUploadFile uploadFile = adminBoardService.getAttachFile(id);
            String storeFilename = uploadFile.getStoreFilename();
            String originFilename = uploadFile.getOriginFilename();

            UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFilename));
            log.info("originFilename={}", originFilename);

            String encodedOriginFilename = UriUtils.encode(originFilename, StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + encodedOriginFilename + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);

        } catch (NotFoundFileException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    /**
     * 모든 어드민 게시판 조회
     * @param page: 페이지의 수
     * @throws: 아무런 게시판이 없는 경우에 BoardNotExistException 발생 (204 no content)
     */
    @ResponseBody
    @GetMapping("/admin-boards")
    public ResponseEntity<AdminBoardsDto> getAdminBoards(@RequestParam("page") int page) {

        try {
            AdminBoardsDto adminBoards = adminBoardService.getAdminBoards(page);
            return ResponseEntity.ok()
                    .body(adminBoards);
        } catch (BoardNotExistException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
    }

    //for test
    @GetMapping(value = "/multipartdata", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> gerMultipartData() {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("first_name",  "ganesh");
        formData.add("last_name", "patil");
        formData.add("file-data_1", new FileSystemResource("C:\\Users\\dnjswo\\study\\project\\muzinut\\file\\sample1.png"));
        return new ResponseEntity<MultiValueMap<String, Object>>(formData, HttpStatus.OK);
    }
}
