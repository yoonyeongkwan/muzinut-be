package nuts.muzinut.controller.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.Lounge;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.board.lounge.DetailLoungeDto;
import nuts.muzinut.dto.board.lounge.LoungesDto;
import nuts.muzinut.dto.board.lounge.LoungesForm;
import nuts.muzinut.exception.NoUploadFileException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.service.board.FileStore;
import nuts.muzinut.service.board.LoungeService;
import nuts.muzinut.service.member.UserService;
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
import java.util.Map;

import static nuts.muzinut.controller.board.FileType.*;

@Slf4j
@Controller
@RequestMapping("/community/lounges")
@RequiredArgsConstructor
public class LoungeController {

    private final UserService userService;
    private final FileStore fileStore;
//    private final FreeBoardService freeBoardService;
    private final LoungeService loungeService;
    private final ObjectMapper objectMapper;

    /**
     * 라운지 게시판 생성
     * @throws NoUploadFileException: 업로드 할 파일이 없는 경우
     * @throws IOException
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<MessageDto> createBoard(
            @RequestPart MultipartFile quillFile) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다."));
        Lounge lounge = new Lounge();
        lounge.addBoard(user);

        Map<FileType, String> filenames = fileStore.storeFile(quillFile);//라운지 게시판 파일 저장
        lounge.setFilename(filenames.get(STORE_FILENAME)); //라운지 파일명 설정
        loungeService.save(lounge); //라운지 게시판 저장
        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/community/lounges/" + lounge.getId())); //생성한 라운지로 리다이렉트

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .headers(header)
                .body(new MessageDto("라운지 게시판이 생성되었습니다"));
    }

    //특정 게시판 조회
    @GetMapping(value = "/{id}",produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> getDetailFreeBoard(@PathVariable Long id) throws JsonProcessingException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();

        DetailLoungeDto detailLoungeDto = loungeService.detailLounge(id);
        String jsonString = objectMapper.writeValueAsString(detailLoungeDto);

        // JSON 데이터를 Multipart-form 데이터에 추가
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> jsonEntity = new HttpEntity<>(jsonString, jsonHeaders);
        formData.add("json_data", jsonEntity);

        //해당 게시판의 quill 파일 추가
        HttpHeaders fileHeaders = new HttpHeaders();
        String quillFilename = detailLoungeDto.getQuillFilename();
        String fullPath = fileStore.getFullPath(quillFilename);
        fileHeaders.setContentType(MediaType.TEXT_HTML); //quill 파일 이므로 html
        formData.add("quillFile", new FileSystemResource(fullPath)); //파일 가져와서 셋팅

        return new ResponseEntity<MultiValueMap<String, Object>>(formData, HttpStatus.OK);
    }

    //Todo 모든 라운지 게시판 조회
    @GetMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> getFreeBoards(
            @RequestParam(value = "page", defaultValue = "0") int page) throws JsonProcessingException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();

        LoungesDto loungesDto = loungeService.getLounges(page);
        String jsonString = objectMapper.writeValueAsString(loungesDto);

        // JSON 데이터를 Multipart-form 데이터에 추가
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> jsonEntity = new HttpEntity<>(jsonString, jsonHeaders);
        formData.add("json_data", jsonEntity);

        //해당 게시판의 quill 파일 추가
        HttpHeaders fileHeaders = new HttpHeaders();
        for (LoungesForm l : loungesDto.getLoungesForms()) {
            String fullPath = fileStore.getFullPath(l.getFilename());
            fileHeaders.setContentType(MediaType.TEXT_HTML); //quill 파일 이므로 html
            formData.add("quillFile", new FileSystemResource(fullPath)); //파일 가져와서 셋팅
        }

        return new ResponseEntity<MultiValueMap<String, Object>>(formData, HttpStatus.OK);
    }
    
    //라운지 게시판 수정
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateFreeBoard(
            @RequestPart @NotNull MultipartFile quillFile, @PathVariable Long id) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("닉네임을 설정해주세요"));
        boolean isAuthorized = loungeService.checkAuth(id, user);
        if (isAuthorized) {
            Lounge lounge = loungeService.getLounge(id);
            //라운지 게시판 파일 저장 및 기존 퀼 파일 삭제
            String changeFilename = fileStore.updateFile(quillFile, lounge.getFilename());
            loungeService.updateLounge(lounge.getId(), changeFilename); //라운지 게시판 저장
            HttpHeaders header = new HttpHeaders();
            header.setLocation(URI.create("/community/lounges/" + lounge.getId())); //수정한 게시판으로 리다이렉트

            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .headers(header)
                    .body(new MessageDto("라운지 게시판이 수정되었습니다"));

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(null);
    }


    //라운지 게시판 삭제
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteFreeBoard(@PathVariable Long id) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("닉네임을 설정해주세요"));
        boolean isAuthorized = loungeService.checkAuth(id, user);

        if (isAuthorized) {
            Lounge lounge = loungeService.getLounge(id); //게시판 조회
            fileStore.deleteFile(lounge.getFilename()); //라운지 게시판의 파일 삭제
            loungeService.deleteLounge(id); //라운지 게시판 삭제

            HttpHeaders header = new HttpHeaders();
            header.setLocation(URI.create("/community/lounges")); //Todo 라운지 게시판 홈페이지로 리다이렉트 (논의 필요)

            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .headers(header)
                    .body(new MessageDto("라운지 게시판이 삭제되었습니다"));

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(null);
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
