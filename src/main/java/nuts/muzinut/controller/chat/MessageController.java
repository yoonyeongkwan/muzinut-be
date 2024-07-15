package nuts.muzinut.controller.chat;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.chat.MessagesDto;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.service.chat.MessageService;
import nuts.muzinut.service.member.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/messages")
    public MessagesDto getMessages(@RequestParam Long chatId) {
        User user = userService.getUserWithUsername().orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다"));
        return messageService.getMessages(chatId);
    }
}
