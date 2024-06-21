package nuts.muzinut.controller.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.email.EmailCheckDto;
import nuts.muzinut.dto.email.EmailRequestDto;
import nuts.muzinut.exception.EmailVertFailException;
import nuts.muzinut.service.member.MailSendService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailSendService mailService;
    @PostMapping("/send")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailDto){
        System.out.println("샐행 =======================================");
        System.out.println("이메일 인증 이메일 :" + emailDto.getEmail());
        return mailService.joinEmail(emailDto.getEmail());
    }

    @PostMapping("/auth-check")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto){
        Boolean Checked = mailService.CheckAuthNum(emailCheckDto.getEmail(),emailCheckDto.getAuthNum());
        if(Checked){
            return "ok";
        }
        else{
            throw new EmailVertFailException("인증 번호가 일치하지 않습니다");
        }

    }

}
