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
import nuts.muzinut.dto.member.profile.Album.ProfileSongDto;
import nuts.muzinut.dto.member.profile.Board.ProfileBoardDto;
import nuts.muzinut.dto.member.profile.Lounge.ProfileDetailLoungeDto;
import nuts.muzinut.dto.member.profile.Lounge.ProfileLoungeDto;
import nuts.muzinut.dto.member.profile.Lounge.ProfileLoungesForm;
import nuts.muzinut.dto.member.profile.PlayNut.ProfilePlayNutDto;
import nuts.muzinut.dto.member.profile.PlayNut.ProfilePlayNutSongDto;
import nuts.muzinut.exception.board.BoardNotFoundException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.service.board.*;
import nuts.muzinut.service.member.ProfileService;
import nuts.muzinut.service.member.UserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    // 프로필 페이지 - 앨범 탭(기본)
    @GetMapping
    public ResponseEntity<?> getUserProfileAlbum(@RequestParam("userId") Long userId) throws JsonProcessingException {
        ProfileSongDto albumTab = profileService.getAlbumTab(userId);
        return new ResponseEntity<ProfileSongDto>(albumTab, HttpStatus.OK);
    }

    // 프로필 페이지 - 라운지 탭
//    @GetMapping(value = "lounge", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @GetMapping(value = "lounge")
    public ResponseEntity<?> getUserProfileLounge(@RequestParam("userId") Long userId) throws IOException {
//        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
//        ProfileLoungeDto profileLoungeDto = profileService.getLoungeTab(userId, 0);
//
//        addJsonEntityToFormData(formData, profileLoungeDto);
//        // 해당 게시판의 quill 파일 추가
//        HttpHeaders fileHeaders = new HttpHeaders();
//        for (ProfileLoungesForm l : profileLoungeDto.getLoungesForms()) {
//            String fullPath = fileStore.getFullPath(l.getFilename());
//            fileHeaders.setContentType(MediaType.TEXT_HTML); // quill 파일이므로 html
//            formData.add("quillFile", new FileSystemResource(fullPath)); // 파일 가져와서 셋팅
//        }
//        return new ResponseEntity<>(formData, HttpStatus.OK);

        ProfileLoungeDto profileLoungeDto = profileService.getLoungeTab(userId, 0);

        for (ProfileLoungesForm l : profileLoungeDto.getLoungesForms()) {
            String fullPath = fileStore.getFullPath(l.getFilename());
            String content = Files.readString(Paths.get(fullPath));
            l.setContent(content); // 파일의 내용을 설정
        }

        return ResponseEntity.ok(profileLoungeDto);

//        try {
//            ProfileLoungeDto profileLoungeDto = profileService.getLoungeTab(userId, 0);
//            return ResponseEntity.ok(profileLoungeDto);
//        } catch (Exception e) {
//            log.error("Failed to get user profile lounge for userId: {}", userId, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to get user profile lounge");
//        }
    }

    // 프로필 페이지 - 게시글 탭
    @GetMapping("/board")
    public ResponseEntity<?> getUserProfileBoard(@RequestParam("userId") Long userId) throws JsonProcessingException {
        String currentUsername = profileService.getCurrentUsername();
        User currentUser = profileService.findUserByUsername(currentUsername);

        if (!currentUser.getId().equals(userId)) {
            throw new NotFoundMemberException("본인의 프로필만 접근 가능");
        } else {
            ProfileBoardDto profileBoardDto = profileService.getBoardTab(userId);
            return new ResponseEntity<>(profileBoardDto, HttpStatus.OK);
        }
    }

    // 프로필 페이지 - 플리넛 탭
    @GetMapping("/playnut")
    public ResponseEntity<?> getUserProfilePlayNuts(@RequestParam("userId") Long userId) throws JsonProcessingException {
        String currentUsername = profileService.getCurrentUsername();
        User currentUser = profileService.findUserByUsername(currentUsername);

        if (!currentUser.getId().equals(userId)) {
            throw new NotFoundMemberException("본인의 프로필만 접근 가능");
        } else {
            ProfilePlayNutDto profilePlayNutDto = profileService.getPlayNutTab(userId);
            return new ResponseEntity<>(profilePlayNutDto, HttpStatus.OK);
        }
    }

    // 플리넛 제목 클릭 시 해당 플리넛에 담긴 곡을 보여주는 메서드
    @GetMapping("/playnut/{playNutId}")
    public ResponseEntity<?> getUserProfilePlayNut(@PathVariable Long playNutId) throws JsonProcessingException {
        ProfilePlayNutSongDto profilePlayNutDto = profileService.getPlayNutSongs(playNutId);
        return new ResponseEntity<>(profilePlayNutDto, HttpStatus.OK);
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
        ProfileDetailLoungeDto profileDetailLoungeDto = profileService.detailLounge(id, findUser);

        if (profileDetailLoungeDto == null) {
            throw new BoardNotFoundException("해당 라운지가 존재하지 않습니다");
        }

        // JSON 데이터를 Multipart-form 데이터에 추가
        addJsonEntityToFormData(formData, profileDetailLoungeDto);

        // 해당 게시판의 quill 파일 추가
        String quillFilename = profileDetailLoungeDto.getQuillFilename();
        String fullPath = fileStore.getFullPath(quillFilename);
        formData.add("quillFile", new FileSystemResource(fullPath)); // 파일 가져와서 셋팅

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
            String redirectUrl = String.format("/profile/lounge?userId=%d", user.getId());
            header.setLocation(URI.create(redirectUrl)); // 라운지 탭으로 리다이렉트

            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .headers(header)
                    .body(new MessageDto("라운지 게시판이 삭제되었습니다"));

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(null);
    }

    // 게시물 제목 클릭 시 특정 게시물 조회로 넘어가는 메소드
    @GetMapping("/community/{id}")
    public ResponseEntity<Void> getBoardDetails(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Map<String, Object> boardDetails = profileService.getBoardDetails(id);
        String boardType = (String) boardDetails.get("boardType");

        String url = buildUrl(boardType, id);
        if (url == null) {
            throw new IllegalArgumentException("Invalid board type: " + boardType);
        }
        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create(url));

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .headers(header)
                .build();
    }

    private String buildUrl(String boardType, Long id) {
        switch (boardType) {
            case "AdminBoard":
                return "/community/admin-boards/" + id;
            case "FreeBoard":
                return "/community/free-boards/" + id;
            case "EventBoard":
                return "/community/event-boards/" + id;
            case "RecruitBoard":
                return "/community/recruit-boards/" + id;
            default:
                return null;
        }
    }
}
