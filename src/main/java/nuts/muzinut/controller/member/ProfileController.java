package nuts.muzinut.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.profile.ProfileSongDto;
import nuts.muzinut.dto.member.profile.ProfileAlbumListDto;
import nuts.muzinut.dto.member.profile.ProfileDto;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.service.board.FileStore;
import nuts.muzinut.service.member.ProfileService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final FileStore fileStore;
    private final ObjectMapper objectMapper;

    // 프로필 정보를 가져오는 메소드
    private MultiValueMap<String, Object> getProfileInfo(Long userId, String tab) throws JsonProcessingException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        ProfileDto profileDto = profileService.getUserProfile(userId);
        addJsonEntityToFormData(formData, "profile-json-data", profileDto);

        // 프로필 이미지와 배너 이미지를 폼 데이터에 추가
        fileStore.setProfileAndBannerImage(profileDto.getProfileImgName(), profileDto.getProfileBannerImgName(), formData);

        // 선택된 탭에 따라 데이터 추가
        switch (tab) {
            case "album":
                MultiValueMap<String, Object> albumData = new LinkedMultiValueMap<>();

                // 메인 곡 데이터 가져오기
                ProfileSongDto mainSong = profileService.getMainSong(userId);
                // 모든 앨범 목록 데이터 가져오기
                List<ProfileAlbumListDto> allAlbums = profileService.getAllAlbums(userId);

                if (mainSong == null && (allAlbums == null || allAlbums.isEmpty())) {
                    addJsonMessageToFormData(albumData, "No Album");
                } else {
                    if (mainSong != null) {
                        addJsonEntityToFormData(albumData, "mainSong-json-data", mainSong);
                        if (mainSong.getAlbumImg() != null) {
                            fileStore.setAlbumImages(mainSong.getAlbumImg(), albumData, "mainSongAlbumImage");
                        }
                    }
                    if (allAlbums != null && !allAlbums.isEmpty()) {
                        addJsonEntityToFormData(albumData, "allAlbums-json-data", allAlbums);
                        for (int i = 0; i < allAlbums.size(); i++) {
                            ProfileAlbumListDto album = allAlbums.get(i);
                            if (album.getAlbumImg() != null) {
                                fileStore.setAlbumImages(album.getAlbumImg(), albumData, "albumImage_" + i);
                            }
                        }
                    }
                }
                formData.addAll(albumData);
                break;

            case "lounge":
                // 라운지 데이터 추가 로직
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
                        addJsonEntityToFormData(formData, "posts-json-data", posts);
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

    // 프로필 페이지 보여주는 메소드
    @GetMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getUserProfile(@RequestParam("userId") Long userId,
                                            @RequestParam(value = "tab", required = false, defaultValue = "album") String tab) throws JsonProcessingException {
        MultiValueMap<String, Object> formData = getProfileInfo(userId, tab);
        log.info("formData = {}", formData);
        return new ResponseEntity<>(formData, HttpStatus.OK);
    }

    // json 메세지를 form-data에 추가하는 메서드
    private void addJsonMessageToFormData(MultiValueMap<String, Object> formData, String message) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(Map.of("message", message));
        HttpHeaders messageHeaders = new HttpHeaders();
        messageHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> messageEntity = new HttpEntity<>(jsonMessage, messageHeaders);
        formData.add("message", messageEntity);
    }

    // 데이터를 json으로 변환하여 form-data에 추가하는 메서드
    private void addJsonEntityToFormData(MultiValueMap<String, Object> formData, String key, Object value) throws JsonProcessingException {
        String jsonValue = objectMapper.writeValueAsString(value);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonValue, headers);
        formData.add(key, entity);
    }
}
