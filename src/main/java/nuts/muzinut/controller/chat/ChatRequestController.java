package nuts.muzinut.controller.chat;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.chat.Chat;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.chat.ChatRequestAcceptForm;
import nuts.muzinut.dto.chat.ChatRequestForm;
import nuts.muzinut.dto.chat.ChatRequestListDto;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.repository.chat.ChatRequestRepository;
import nuts.muzinut.service.chat.ChatRequestService;
import nuts.muzinut.service.chat.ChatService;
import nuts.muzinut.service.chat.MessageService;
import nuts.muzinut.service.member.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request-chat")
@RequiredArgsConstructor
public class ChatRequestController {

    private final UserService userService;
    private final ChatRequestService chatRequestService;
    private final ChatService chatService;
    private final MessageService messageService;

    /**
     * 채팅방 참여 요청
     * @param form: 채팅방 참가 요청을 보낼 사용자의 닉네임
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public MessageDto createChatRequest(@RequestBody @Validated ChatRequestForm form) {
        User requestUser = userService.getUserWithUsername().orElseThrow(() -> new NotFoundMemberException("채팅방 요청을 만들 수 없습니다"));
        User receiveUser = userService.findByNickname(form.getNickname()).
                orElseThrow(() -> new NotFoundMemberException("메시지를 요청할 사람이 없습니다"));
        chatRequestService.createRequest(requestUser, receiveUser, form.getMessage());
        return new MessageDto("메시지 요청이 성공하였습니다");
    }

    /**
     * 채팅방 참여 요청 수락
     * @param form: 채팅방 참가 요청을 보낸 사용자의 닉네임
     */
    @PostMapping("/accept")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public MessageDto acceptChatRequest(@RequestBody @Validated ChatRequestAcceptForm form) {
        User receiveUser = userService.getUserWithUsername().orElseThrow(() -> new NotFoundMemberException("채팅방 요청을 수락 할 수 없습니다"));
        User requestUser = userService.findByNickname(form.getNickname()).orElseThrow(() -> new NotFoundMemberException("메시지를 요청한 사람이 없습니다"));
        chatRequestService.acceptRequest(requestUser, receiveUser);

        Chat chat = chatService.createRoom(requestUser, receiveUser);
        messageService.createMessage(chat.getId(), requestUser, receiveUser, form.getMessage()); //메시지 생성
        return new MessageDto("채팅방 요청을 수락하였습니다");
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ChatRequestListDto requestList() {
        User user = userService.getUserWithUsername().orElseThrow(() -> new NotFoundMemberException("채팅방 요청을 수락 할 수 없습니다"));
        return chatRequestService.requestList(user);
    }
}
