package nuts.muzinut.controller.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.dto.EmailCheckDto;
import nuts.muzinut.dto.EmailRequestDto;
import nuts.muzinut.service.member.MailSendService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailSendService mailService;

    @PostMapping("/mailSend")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailDto, BindingResult bindingResult) {
        log.info("이메일 인증 이메일: {}", emailDto.getEmail());
        return mailService.joinEmail(emailDto.getEmail());
    }

    @PostMapping("/mailauthCheck")
    public String authCheck(@RequestBody @Valid EmailCheckDto emailCheckDto) {
        Boolean Checked = mailService.CheckAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());

        if (Checked) {
            return "ok";
        } else {
            throw new NullPointerException("뭔가 잘못!");
        }
    }
}
