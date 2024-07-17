package nuts.muzinut.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.exception.chat.InvalidChatRoomException;
import nuts.muzinut.jwt.TokenProvider;
import nuts.muzinut.service.chat.ChatService;
import nuts.muzinut.service.chat.MessageService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatPreHandler implements ChannelInterceptor {

    private final TokenProvider tokenProvider;
    private final ChatService chatService;
    private final MessageService messageService;
    private String username;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        //채팅방 연결
        if (StompCommand.CONNECT == accessor.getCommand()) {

            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            log.info("connect: {}", jwtToken);
            //권한 확인
            if (StringUtils.hasText(jwtToken)) {
                String token = jwtToken.substring(7);
                username = tokenProvider.validateToken(token);//토큰 검증
            } else {
                throw new MessageDeliveryException(message, new NotFoundMemberException("채팅방 입장 권한이 없습니다"));
            }

            //채팅방 연결
            try {
                connectToChatRoom(accessor, username);
            } catch (AccessDeniedException | InvalidChatRoomException e) {
                throw new MessageDeliveryException(message, e);
            }
        }

        //메시지 보내기
        if (StompCommand.SEND == accessor.getCommand()) {
            log.info("메시지 전송!");
            log.info("경로: {}", accessor.getDestination());
            String roomNumber;
            if (StringUtils.hasText(accessor.getDestination())) {
                roomNumber = getRoomNumber(accessor.getDestination());
            } else {
                throw new MessageDeliveryException(message, new InvalidChatRoomException("메시지를 보낼 경로를 입력하세요"));
            }
            if (!chatService.isValidChatRoom(Long.parseLong(roomNumber))) {
                throw new MessageDeliveryException(message, new InvalidChatRoomException("유효하지 않는 채팅방에 메시지를 보낼 수 없습니다"));
            }
        }

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.DISCONNECT == accessor.getCommand()) {
            log.info("{} 채팅룸 handleMessage: 연결 끊음!!", accessor.getDestination());
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            log.info("연결 끊는 토큰: {}", jwtToken);
            if (StringUtils.hasText(jwtToken)) {
                String token = jwtToken.substring(7);
                String username = tokenProvider.validateToken(token);//토큰 검증
                disconnectChatRoom(accessor, username);
            }
        }
    }

    //채팅방 입장 메서드
    private void connectToChatRoom(StompHeaderAccessor accessor, String username) {
        String roomNumber; //입장하고자 하는 채팅방 번호
        if (StringUtils.hasText(accessor.getDestination())) {
            roomNumber = getRoomNumber(accessor.getDestination()); //채팅창 번호를 가져온다
        } else {
            log.info("잘못된 채팅방으로 접근");
            throw new AccessDeniedException("잘못된 채팅방으로 접근하였습니다");
        }

        chatService.connectChatRoom(roomNumber, username); //redis 에 채팅방 참가 인원 추가
    }

    //채팅방 퇴장 메서드
    private void disconnectChatRoom(StompHeaderAccessor accessor, String username) {
        String roomNumber; //퇴장하고자 하는 채팅방 번호
        if (StringUtils.hasText(accessor.getDestination())) {
            roomNumber = getRoomNumber(accessor.getDestination()); //채팅창 번호를 가져온다
        } else {
            throw new AccessDeniedException("잘못된 방법으로 채팅방 탈퇴시도하였습니다.");
        }

        chatService.disconnectChatRoom(roomNumber, username); //redis 에 채팅방 참가 인원 수정
    }

    //방 번호를 추출하는 메서드 ex) /room/1 -> 1 추출
    private String getRoomNumber(String fullRoomPath) {
//        log.info("fullRoomPath: {}", fullRoomPath);
        int pos = fullRoomPath.lastIndexOf("/");
        return fullRoomPath.substring(pos + 1);
    }
}
