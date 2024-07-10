package nuts.muzinut.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.controller.board.FileType;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.member.*;
import nuts.muzinut.dto.member.follow.ProfileUpdateDto;
import nuts.muzinut.dto.member.profile.ProfileDto;
import nuts.muzinut.dto.security.RefreshTokenDto;
import nuts.muzinut.dto.security.TokenDto;
import nuts.muzinut.exception.EmailVertFailException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.jwt.JwtFilter;
import nuts.muzinut.jwt.TokenProvider;
import nuts.muzinut.service.board.FileStore;
import nuts.muzinut.service.member.MailSendService;
import nuts.muzinut.service.member.ProfileService;
import nuts.muzinut.service.member.UserService;
import nuts.muzinut.service.security.AuthService;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static nuts.muzinut.controller.board.FileType.*;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileStore fileStore;
    private final MailSendService mailService;
    private final ProfileService profileService;
    private final TokenProvider tokenProvider;
    private final AuthService authService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ObjectMapper objectMapper;

    @GetMapping("/join")
    public String joinForm() {
        return "/user/join";
    }

    /**
     * @param joinDto
     * @throws: 이메일 인증 실패시 404 error
     * @return: 회원가입 성공 메시지 전송
     */
    @ResponseBody
    @PostMapping("/join")
    public MessageDto join(@Validated @RequestBody JoinDto joinDto) {
        Boolean Checked = mailService.CheckAuthNum(joinDto.getUsername(), joinDto.getAuthNum());
        if (Checked) {
            userService.signup(new UserDto(joinDto.getUsername(), joinDto.getPassword()));
            return new MessageDto("회원 가입 성공");
        } else {
            throw new EmailVertFailException("인증 번호가 일치하지 않습니다");
        }
    }

    /**
     * 사실상 로그인 하는 로직
     * @param loginDto: username (email) & password 전송 필요
     * @return:
     */
    @ResponseBody
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> login(@Validated @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);

        return new ResponseEntity<>(new TokenDto(token, refreshToken), httpHeaders, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/re-authenticate")
    public TokenDto reLogin(@RequestHeader("Authorization") String bearerToken) {
        return authService.reissueToken(bearerToken.substring(7)); //bearer 제외한 실제 토큰 값만 반환
    }

    /**
     * 사용자의 프로필을 설정하는 메서드
     * @param profileImg: 사용자가 설정하고 싶은 프로필 이미지
     * @throws IOException
     * @return: 리다이랙트 필요
     */
    @ResponseBody //Todo 리다이렉트 설정 필요
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping(value = "/set-profile")
    public MessageDto setProfile(@RequestPart("profileImg") MultipartFile profileImg) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다."));

        if (StringUtils.hasText(user.getProfileImgFilename())) {
            //프로필 바꾸기
            String changeImgName = fileStore.updateFile(profileImg, user.getProfileImgFilename());
            userService.setProfileName(changeImgName, user);
        } else {
            //프로필 처음 설정
            Map<FileType, String> filenames = fileStore.storeFile(profileImg);
            userService.setProfileName(filenames.get(STORE_FILENAME), user);
        }

        return new MessageDto("파일 저장 성공");
    }

    //프로필 닉네임, 자기소개 설정
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/set-profile-nickname-intro")
    public MessageDto setProfileNicknameIntro(@Validated @RequestBody ProfileUpdateDto profileUpdateDto) {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다."));

        userService.updateNicknameAndIntro(user.getId(), profileUpdateDto.getNickname(), profileUpdateDto.getIntro());
        return new MessageDto("프로필 업데이트가 성공되었습니다. ");
    }

    // 프로필 배너 이미지 설정
    @ResponseBody //Todo 리다이렉트 설정 필요
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping(value = "/set-profile-bannerImage")
    public MessageDto setProfileBannerImage(@RequestParam("bannerImg") MultipartFile bannerImg) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다."));

        if (StringUtils.hasText(user.getProfileBannerImgFilename())) {
            //프로필 배너 바꾸기
            String changeImgName = fileStore.updateFile(bannerImg, user.getProfileBannerImgFilename());
            userService.setProfileBannerName(changeImgName, user);
        } else {
            //프로필 배너 처음 설정
            Map<FileType, String> filenames = fileStore.storeFile(bannerImg);
            userService.setProfileBannerName(filenames.get(STORE_FILENAME), user);
        }

        return new MessageDto("파일 저장 성공");
    }

    // 프로필 페이지 보여주는 메소드
    @ResponseBody
    @GetMapping(value = "/profile", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
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
        fileStore.setProfileAndBannerImage(profileDto.getProfileImgName(), profileDto.getProfileBannerImgName(), formData);

        return new ResponseEntity<>(formData, HttpStatus.OK);
    }

    // 마이페이지 - 내가 작성한 게시글을 보여주는 메소드
    @ResponseBody
    @GetMapping("/profile-community")
    public ResponseEntity<List<String>> getUserBoardTitles(@RequestParam("userId") Long userId) {
        // 현재 로그인한 사용자의 ID 가져오기
        String currentUsername = profileService.getCurrentUsername();
        User currentUser = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다."));

        // 요청한 userId가 현재 로그인한 사용자의 ID와 일치하는지 확인
        if (!currentUser.getId().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // 유저가 작성한 게시글 제목 리스트 가져오기
        List<String> boardTitles = profileService.getUserBoardTitles(userId);
        return new ResponseEntity<>(boardTitles, HttpStatus.OK);
    }
    //비밀번호 수정
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/change-password")
    public MessageDto changePassword(@Validated @RequestBody PasswordChangeForm form) {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다."));

        userService.updatePassword(user, form.getCurrentPassword(), form.getNewPassword());
        return new MessageDto("비밀번호가 수정되었습니다");
    }

    //비밀번호 찾기
    @ResponseBody
    @PostMapping("/find-password")
    public MessageDto findPassword(@RequestBody @Validated @NotNull PasswordFindForm form) {
        if (userService.isUser(form.getUsername())) {
            mailService.findPassword(form.getUsername());
            return new MessageDto("이메일이 전송되었습니다");
        }
        throw new NotFoundMemberException("가입된 회원이 아닙니다. 회원가입을 해주세요");
    }

    //비밀번호 새롭게 설정하기
    @ResponseBody
    @PutMapping("/find-password")
    public MessageDto setNewPassword(@RequestBody @Validated SetNewPasswordForm form) {
        Boolean checked = mailService.CheckAuthNum(form.getUsername(), form.getAuthNum());
        if (checked) {
            User findUser = userService.findByEmail(form.getUsername());
            userService.updatePassword(findUser, form.getPassword());
            return new MessageDto("비밀번호가 수정되었습니다");
        }
        throw new EmailVertFailException("인증 번호가 일치하지 않습니다");
    }

}
