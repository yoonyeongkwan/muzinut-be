package nuts.muzinut.controller.member;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.controller.board.FileType;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.member.*;
import nuts.muzinut.dto.security.TokenDto;
import nuts.muzinut.exception.EmailVertFailException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.jwt.JwtFilter;
import nuts.muzinut.jwt.TokenProvider;
import nuts.muzinut.service.board.FileStore;
import nuts.muzinut.service.member.MailSendService;
import nuts.muzinut.service.member.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
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

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
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
