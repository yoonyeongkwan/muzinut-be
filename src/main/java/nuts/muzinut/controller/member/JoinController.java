package nuts.muzinut.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class JoinController {

    @GetMapping("/join")
    public String join() {
        return "/user/join";
    }
}
