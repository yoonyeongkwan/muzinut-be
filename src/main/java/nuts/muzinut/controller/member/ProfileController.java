package nuts.muzinut.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.ProfileDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.service.board.FileStore;
import nuts.muzinut.service.member.ProfileService;
import nuts.muzinut.service.member.UserService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final FileStore fileStore;
    private final ObjectMapper objectMapper;

    // 프로필 페이지 보여주는 메소드
    @ResponseBody
    @GetMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> getUserProfile(@RequestParam("userId") Long userId) throws JsonProcessingException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        ProfileDto profileDto = profileService.getUserProfile(userId);
        String jsonString = objectMapper.writeValueAsString(profileDto);

        // JSON 데이터를 Multipart-form 데이터에 추가
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> jsonEntity = new HttpEntity<>(jsonString, jsonHeaders);
        formData.add("json_data", jsonEntity);

        // 프로필 이미지와 배너 이미지를 폼 데이터에 추가
        log.debug("Setting profile image: " + profileDto.getProfileImgName());
        log.debug("Setting banner image: " + profileDto.getProfileBannerImgName());
        fileStore.setProfileAndBannerImage(profileDto.getProfileImgName(), profileDto.getProfileBannerImgName(), formData);

        return new ResponseEntity<>(formData, HttpStatus.OK);
    }

    // 탭 내용 가져오는 메소드
    @GetMapping("/{tab}")
    public ResponseEntity<?> getProfileTabContent(@PathVariable String tab, @RequestParam("userId") Long userId) throws JsonProcessingException {
        // 현재 로그인한 사용자 정보 가져오기
        String currentUsername = profileService.getCurrentUsername();
        User currentUser = profileService.findUserByUsername(currentUsername);

        // 각 탭에 대한 내용 처리
        switch (tab) {
            case "album":
//                // 앨범 데이터 가져오기
//                List<AlbumDto> albums = profileService.getUserAlbums(userId);
//                return new ResponseEntity<>(albums, HttpStatus.OK);

            case "lounge":
//                // 라운지 데이터 가져오기
//                List<LoungeDto> lounges = profileService.getUserLounges(userId);
//                return new ResponseEntity<>(lounges, HttpStatus.OK);

            case "community":
                // 로그인한 사용자와 프로필 사용자가 동일한지 확인
                if (!currentUser.getId().equals(userId)) {
                    return new ResponseEntity<>("본인의 프로필만 접근 가능", HttpStatus.FORBIDDEN);
                }
                // 게시글 데이터 가져오기
                List<String> posts = profileService.getUserBoardTitles(userId);
                return new ResponseEntity<>(posts, HttpStatus.OK);

            case "nuts":
                // 로그인한 사용자와 프로필 사용자가 동일한지 확인
//                if (!currentUser.getId().equals(userId)) {
//                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//                }
//                // 넛츠 데이터 가져오기
//                List<NutsDto> nuts = profileService.getUserNuts(userId);
//                return new ResponseEntity<>(nuts, HttpStatus.OK);

            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
