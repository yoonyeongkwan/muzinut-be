package nuts.muzinut.controller.member;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.controller.board.FileType;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.member.JoinDto;
import nuts.muzinut.dto.member.LoginDto;
import nuts.muzinut.dto.member.UserDto;
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
import org.springframework.http.MediaType;
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
        if(Checked){
            userService.signup(new UserDto(joinDto.getUsername(), joinDto.getPassword()));
            return new MessageDto("회원 가입 성공");
        }
        else{
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

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping(value = "/set-profile", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageDto> setProfile(MultipartFile profileImg) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다.")); //진짜?

        if (StringUtils.hasText(user.getProfileImgFilename())) {
            //프로필 바꾸기
            String changeImgName = fileStore.updateFile(profileImg, user.getProfileImgFilename());
        } else {
            //프로필 처음 설정
            Map<FileType, String> filenames = fileStore.storeFile(profileImg);
            user.changeProfileImg(filenames.get(STORE_FILENAME)); //파일명 설정.
        }
        return null;
    }

}
