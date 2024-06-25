package nuts.muzinut.controller.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.board.free.DetailFreeBoardDto;
import nuts.muzinut.dto.board.free.FreeBoardForm;
import nuts.muzinut.dto.board.free.FreeBoardsDto;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.exception.*;
import nuts.muzinut.service.board.FileStore;
import nuts.muzinut.service.board.FreeBoardService;
import nuts.muzinut.service.security.UserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/free-boards")
@RequiredArgsConstructor
public class FreeBoardController {

    private final UserService userService;
    private final FileStore fileStore;
    private final FreeBoardService freeBoardService;
    private final ObjectMapper objectMapper;

    /**
     * 자유 게시판 생성
     * @param freeBoardForm: react quill file & title
     * @throws NoUploadFileException: 업로드 할 파일이 없는 경우
     * @throws IOException
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<MessageDto> createBoard(@ModelAttribute FreeBoardForm freeBoardForm) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다."));
        FreeBoard freeBoard = new FreeBoard(freeBoardForm.getTitle());
        freeBoard.addBoard(user);

        try {
            fileStore.storeFile(freeBoardForm.getQuillFile(), freeBoard); //자유 게시판 파일 저장
            freeBoardService.save(freeBoard); //자유 게시판 저장
            HttpHeaders header = new HttpHeaders();
            header.setLocation(URI.create("/free-boards/" + freeBoard.getId())); //수정한 게시판으로 리다이렉트

            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .headers(header)
                    .body(new MessageDto("자유 게시판이 생성되었습니다"));
        } catch (NoUploadFileException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    //특정 게시판 조회
    @GetMapping(value = "/{id}",produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> getDetailFreeBoard(@PathVariable Long id) throws JsonProcessingException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();

        DetailFreeBoardDto detailFreeBoardDto = freeBoardService.detailFreeBoard(id);
        String jsonString = objectMapper.writeValueAsString(detailFreeBoardDto);

        // JSON 데이터를 Multipart-form 데이터에 추가
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> jsonEntity = new HttpEntity<>(jsonString, jsonHeaders);
        formData.add("json_data", jsonEntity);

        //해당 게시판의 quill 파일 추가
        HttpHeaders fileHeaders = new HttpHeaders();
        String quillFilename = detailFreeBoardDto.getQuillFilename();
        String fullPath = fileStore.getFullPath(quillFilename);
        fileHeaders.setContentType(MediaType.TEXT_HTML);
        formData.add("quillFile", new FileSystemResource(fullPath));

        return new ResponseEntity<MultiValueMap<String, Object>>(formData, HttpStatus.OK);
    }

    //모든 자유 게시판 조회
    @GetMapping()
    public ResponseEntity<FreeBoardsDto> getFreeBoards(@RequestParam("page") int page) {
        try {
            FreeBoardsDto freeBoards = freeBoardService.getFreeBoards(page);
            return ResponseEntity.ok()
                    .body(freeBoards);
        } catch (BoardNotExistException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
    }
    
    //자유 게시판 수정
/*    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateBoard(@ModelAttribute FreeBoardForm freeBoardForm, @PathVariable Long id) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다."));
        boolean isAuthorized = freeBoardService.checkAuth(id, user);
        if (isAuthorized) {
            try {
                fileStore.storeFile(freeBoardForm.getQuillFile(), freeBoard); //자유 게시판 파일 저장
                freeBoardService.save(freeBoard); //자유 게시판 저장
                HttpHeaders header = new HttpHeaders();
                header.setLocation(URI.create("/free-boards/" + freeBoard.getId())); //수정한 게시판으로 리다이렉트

                return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                        .headers(header)
                        .body(new MessageDto("자유 게시판이 생성되었습니다"));
            } catch (NoUploadFileException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(null);
    }*/


    //for test
    @GetMapping(value = "/multipartdata", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> gerMultipartData() {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("first_name",  "ganesh");
        formData.add("last_name", "patil");
        formData.add("file-data_1", new FileSystemResource("C:\\Users\\dnjswo\\study\\project\\muzinut\\file\\sample1.png"));
        return new ResponseEntity<MultiValueMap<String, Object>>(formData, HttpStatus.OK);
    }

    @GetMapping("/ex")
    public void ex() {
        throw new NotFoundEntityException("고쳐져라 제발");
    }
}
