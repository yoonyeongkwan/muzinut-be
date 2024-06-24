package nuts.muzinut.controller.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.board.free.FreeBoardForm;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.service.security.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/free-boards")
@RequiredArgsConstructor
public class FreeBoardController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<MessageDto> createBoard(@ModelAttribute FreeBoardForm freeBoardForm) {
        UserDto user = userService.getMyUserWithAuthorities();
        String nickname = user.getNickname();

        return null;
    }
}
