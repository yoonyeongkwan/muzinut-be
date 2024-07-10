package nuts.muzinut.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.controller.board.FileType;
import nuts.muzinut.domain.board.Lounge;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.board.lounge.DetailLoungeDto;
import nuts.muzinut.dto.board.lounge.LoungesDto;
import nuts.muzinut.dto.board.lounge.LoungesForm;
import nuts.muzinut.dto.member.profile.ProfileDto;
import nuts.muzinut.dto.member.profile.ProfileSongDto;
import nuts.muzinut.exception.BoardNotFoundException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.service.board.*;
import nuts.muzinut.service.member.ProfileService;
import nuts.muzinut.service.member.UserService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static nuts.muzinut.controller.board.FileType.STORE_FILENAME;

@Slf4j
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final LoungeService loungeService;
    private final UserService userService;
    private final FileStore fileStore;
    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    // 프로필 정보를 가져오는 메소드
    private MultiValueMap<String, Object> getProfileInfo(Long userId, String tab) throws JsonProcessingException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        // 선택된 탭에 따라 데이터 추가
        switch (tab) {
            case "album":
                MultiValueMap<String, Object> albumData = new LinkedMultiValueMap<>();

                // 메인 곡 데이터 가져오기
                ProfileSongDto albumTab = profileService.getAlbumTab(userId);

                if (albumTab == null) {
                    addJsonEntityToFormData(albumData, profileService.getUserProfile(userId));
                } else {
                    addJsonEntityToFormData(albumData,  albumTab);
                }
                formData.addAll(albumData);
                break;

            case "lounge":
                // 모든 라운지 조회 메서드
                MultiValueMap<String, Object> loungeData = new LinkedMultiValueMap<String, Object>();
                LoungesDto loungesDto = loungeService.getLoungesByUserId(userId, 0);
                if (loungesDto == null || loungesDto.getLoungesForms().isEmpty()) {
                    addJsonMessageToFormData(formData, "No Lounge");
                } else {
                    addJsonEntityToFormData(formData, loungesDto);
                    //해당 게시판의 quill 파일 추가
                    HttpHeaders fileHeaders = new HttpHeaders();
                    for (LoungesForm l : loungesDto.getLoungesForms()) {
                        String fullPath = fileStore.getFullPath(l.getFilename());
                        fileHeaders.setContentType(MediaType.TEXT_HTML); //quill 파일 이므로 html
                        formData.add("quillFile", new FileSystemResource(fullPath)); //파일 가져와서 셋팅
                    }
                }

                formData.addAll(loungeData);
                break;

            case "board":
                // 현재 로그인한 사용자 정보 가져오기
                String currentUsername = profileService.getCurrentUsername();
                User currentUser = profileService.findUserByUsername(currentUsername);
                // 로그인한 사용자와 프로필 사용자가 동일한지 확인
                if (!currentUser.getId().equals(userId)) {
                    throw new NotFoundMemberException("본인의 프로필만 접근 가능");
                } else {
                    // 게시글 데이터 추가 로직
                    List<String> posts = profileService.getUserBoardTitles(userId);
                    if (posts == null || posts.isEmpty()) {
                        addJsonMessageToFormData(formData, "No Board");
                    } else {
                        addJsonEntityToFormData(formData, posts);
                    }
                }
                break;

            case "playNut":
                // 플리넛 데이터 추가 로직
                break;

            case "nuts":
                // 넛츠 데이터 추가 로직
                break;
        }

        return formData;
    }

    // 프로필 페이지 - 앨범 탭(기본)
    @GetMapping()
    public ResponseEntity<?> getUserProfileAlbum(@RequestParam("userId") Long userId,
                                            @RequestParam(value = "tab", required = false, defaultValue = "album") String tab) throws JsonProcessingException {
        // 메인 곡 데이터 가져오기
        ProfileSongDto albumTab = profileService.getAlbumTab(userId);
        return new ResponseEntity<ProfileSongDto>(albumTab, HttpStatus.OK);
    }

    // 프로필 페이지 - 라운지 탭
    @GetMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getUserProfileLounge(@RequestParam("userId") Long userId,
                                            @RequestParam(value = "tab", required = false, defaultValue = "album") String tab) throws JsonProcessingException {
        MultiValueMap<String, Object> formData = getProfileInfo(userId, tab);
        log.info("formData = {}", formData);
        return new ResponseEntity<>(formData, HttpStatus.OK);
    }


//    // 프로필 페이지 보여주는 메소드
//    @GetMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> getUserProfile(@RequestParam("userId") Long userId,
//                                            @RequestParam(value = "tab", required = false, defaultValue = "album") String tab) throws JsonProcessingException {
//        MultiValueMap<String, Object> formData = getProfileInfo(userId, tab);
//        log.info("formData = {}", formData);
//        return new ResponseEntity<>(formData, HttpStatus.OK);
//    }

    // json 메세지를 form-data에 추가하는 메서드
    private void addJsonMessageToFormData(MultiValueMap<String, Object> formData, String message) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(Map.of("message", message));
        HttpHeaders messageHeaders = new HttpHeaders();
        messageHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> messageEntity = new HttpEntity<>(jsonMessage, messageHeaders);
        formData.add("message", messageEntity);
    }

    // 데이터를 json으로 변환하여 form-data에 추가하는 메서드
    private void addJsonEntityToFormData(MultiValueMap<String, Object> formData, Object value) throws JsonProcessingException {
        String jsonValue = objectMapper.writeValueAsString(value);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonValue, headers);
        formData.add("json-data", entity);
    }

    // 라운지 생성 메소드
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping(value = "/lounge")
    public ResponseEntity<MessageDto> createBoard(
            @RequestPart MultipartFile quillFile) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다."));
        Lounge lounge = new Lounge();
        lounge.addBoard(user);

        Map<FileType, String> filenames = fileStore.storeFile(quillFile); //라운지 게시판 파일 저장
        lounge.setFilename(filenames.get(STORE_FILENAME)); //라운지 파일명 설정
        loungeService.save(lounge); //라운지 게시판 저장
        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/profile/lounges/" + lounge.getId())); //생성한 라운지로 리다이렉트

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .headers(header)
                .body(new MessageDto("라운지 게시판이 생성되었습니다"));
    }

    // 라운지 댓글 클릭시 특정 라운지의 댓글, 대댓글 조회하는 메소드
    @GetMapping(value = "/lounge/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> getDetailLounge(@PathVariable Long id) throws JsonProcessingException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();

        User findUser = userService.getUserWithUsername().orElse(null);
        DetailLoungeDto detailLoungeDto = loungeService.detailLounge(id, findUser);

        if (detailLoungeDto == null) {
            throw new BoardNotFoundException("해당 라운지가 존재하지 않습니다");
        }

        // JSON 데이터를 Multipart-form 데이터에 추가
        addJsonEntityToFormData(formData, detailLoungeDto);

        // 해당 게시판의 quill 파일 추가
        String quillFilename = detailLoungeDto.getQuillFilename();
        String fullPath = fileStore.getFullPath(quillFilename);
        formData.add("quillFile", new FileSystemResource(fullPath)); // 파일 가져와서 셋팅

//        // 댓글 및 대댓글 작성자의 프로필 이미지 추가
//        Set<String> profileImages = loungeService.getProfileImages(detailLoungeDto.getProfileImg(),
//                detailLoungeDto.getComments());
//        fileStore.setImageHeaderWithData(profileImages, formData);

        return new ResponseEntity<>(formData, HttpStatus.OK);
    }

    // 라운지 수정 메서드
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/lounge/{id}")
    public ResponseEntity<MessageDto> updateLounges(
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
            header.setLocation(URI.create("/profile/lounge/" + lounge.getId())); //수정한 게시판으로 리다이렉트

            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .headers(header)
                    .body(new MessageDto("라운지 게시판이 수정되었습니다"));

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(null);
    }

    //라운지 게시판 삭제
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/lounge/{id}")
    public ResponseEntity<MessageDto> deleteLounges(@PathVariable Long id) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("닉네임을 설정해주세요"));
        boolean isAuthorized = loungeService.checkAuth(id, user);

        if (isAuthorized) {
            Lounge lounge = loungeService.getLounge(id); //게시판 조회
            fileStore.deleteFile(lounge.getFilename()); //라운지 게시판의 파일 삭제
            loungeService.deleteLounge(id); //라운지 게시판 삭제

            HttpHeaders header = new HttpHeaders();
            String redirectUrl = String.format("/profile?userId=%d&tab=lounge", user.getId());
            header.setLocation(URI.create(redirectUrl)); // 라운지 탭으로 리다이렉트

            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .headers(header)
                    .body(new MessageDto("라운지 게시판이 삭제되었습니다"));

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(null);
    }

    // 게시물 제목 클릭 시 특정 게시물 조회로 넘어가는 메소드
    @GetMapping(value = "/community/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> getBoardDetails(@PathVariable Long id) {
        Map<String, Object> boardDetails = profileService.getBoardDetails(id);
        String boardType = (String) boardDetails.get("boardType");
        ResponseEntity<MultiValueMap<String, Object>> responseEntity;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity;

        switch (boardType) {
            case "AdminBoard":
            case "FreeBoard":
            case "EventBoard":
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                entity = new HttpEntity<>(headers);
                responseEntity = restTemplate.exchange(
                        "http://localhost:8080/community/" + boardType.toLowerCase() + "-boards/" + id,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<MultiValueMap<String, Object>>() {}
                );
                break;
            case "RecruitBoard":
                headers.setContentType(MediaType.APPLICATION_JSON);
                entity = new HttpEntity<>(headers);
                responseEntity = restTemplate.exchange(
                        "http://localhost:8080/community/recruit-boards/" + id,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<MultiValueMap<String, Object>>() {}
                );
                break;
            default:
                throw new IllegalArgumentException("Invalid board type: " + boardType);
        }

        return responseEntity;
    }

}
