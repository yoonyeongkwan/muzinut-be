package nuts.muzinut.controller.member;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.service.member.MailSendService;
import nuts.muzinut.service.member.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TokenTestController {

    private final UserService userService;
    private final MailSendService mailService;

    @GetMapping("/token-test")
    public String tokenTest() {
        return "/user/token-test";
    }
}
