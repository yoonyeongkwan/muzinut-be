package nuts.muzinut.controller.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.dto.email.EmailCheckDto;
import nuts.muzinut.dto.email.EmailRequestDto;
import nuts.muzinut.exception.EmailVertFailException;
import nuts.muzinut.service.member.MailSendService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailSendService mailService;
    @PostMapping("/send")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailDto){
        log.info("이메일 인증 이메일 :{}", emailDto.getUsername());
        return mailService.joinEmail(emailDto.getUsername());
    }

    @PostMapping("/auth-check")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto){
        Boolean Checked = mailService.CheckAuthNum(emailCheckDto.getUsername(),emailCheckDto.getAuthNum());
        if(Checked){
            return "ok";
        }
        else{
            throw new EmailVertFailException("인증 번호가 일치하지 않습니다");
        }
    }

}
