package nuts.muzinut.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Album;
import nuts.muzinut.dto.member.profile.ProfileSongDto;
import nuts.muzinut.dto.member.profile.ProfileAlbumListDto;
import nuts.muzinut.dto.member.profile.ProfileDto;
import nuts.muzinut.service.board.FileStore;
import nuts.muzinut.service.member.ProfileService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/{tab}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getProfileTabContent(@PathVariable String tab, @RequestParam("userId") Long userId) throws JsonProcessingException {


        // 각 탭에 대한 내용 처리
        switch (tab) {
            case "album":
                // 메인 곡 데이터 가져오기
                ProfileSongDto mainSong = profileService.getMainSong(userId);

                // 모든 앨범 목록 데이터 가져오기
                List<ProfileAlbumListDto> allAlbums = profileService.getAllAlbums(userId);

                MultiValueMap<String, Object> albumData = new LinkedMultiValueMap<>();

                // 메인 곡 데이터를 JSON으로 변환하여 추가
                String mainSongJson = objectMapper.writeValueAsString(mainSong);
                HttpHeaders mainSongHeaders = new HttpHeaders();
                mainSongHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> mainSongEntity = new HttpEntity<>(mainSongJson, mainSongHeaders);
                albumData.add("mainSong", mainSongEntity);

                // 모든 앨범 목록 데이터를 JSON으로 변환하여 추가
                String allAlbumsJson = objectMapper.writeValueAsString(allAlbums);
                HttpHeaders allAlbumsHeaders = new HttpHeaders();
                allAlbumsHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> allAlbumsEntity = new HttpEntity<>(allAlbumsJson, allAlbumsHeaders);
                albumData.add("allAlbums", allAlbumsEntity);

                // 메인 곡의 앨범 이미지 파일을 추가
                if (mainSong != null && mainSong.getAlbumImg() != null) {
                    fileStore.setAlbumImages(mainSong.getAlbumImg(), albumData);
                }

                // 모든 앨범 이미지 파일을 추가
                for (ProfileAlbumListDto album : allAlbums) {
                    fileStore.setAlbumImages(album.getAlbumImg(), albumData);
                }

                return new ResponseEntity<>(albumData, HttpStatus.OK);

            case "lounge":
//                // 라운지 데이터 가져오기
//                List<LoungeDto> lounges = profileService.getUserLounges(userId);
//                return new ResponseEntity<>(lounges, HttpStatus.OK);

            case "board":
                // 현재 로그인한 사용자 정보 가져오기
                String currentUsername = profileService.getCurrentUsername();
                User currentUser = profileService.findUserByUsername(currentUsername);
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
