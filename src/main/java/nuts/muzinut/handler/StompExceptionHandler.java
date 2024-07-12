package nuts.muzinut.handler;

import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.exception.chat.InvalidChatRoomException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class StompExceptionHandler extends StompSubProtocolErrorHandler {

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        Throwable exception = convertThrowException(ex); //MessageDeliveryException 의 cause 예외를 꺼내는 메서드

        if (exception instanceof InvalidChatRoomException) { //유효하지 않는 채팅방에 입장하려는 경우
            return errorMessage(exception.getMessage());
        } else if (exception instanceof NotFoundMemberException) { //권한이 없는 사용자가 채팅방에 입장하려는 경우
            return errorMessage(exception.getMessage());
        } else if (exception instanceof AccessDeniedException) { //잘못된 방에 입장하려는 경우
            return errorMessage(exception.getMessage());
        }
        return errorMessage("채팅방 오류 발생");

//        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    /**
     * 오류 메시지를 포함한 Message 객체를 생성
     * @param errorMessage 오류 메시지
     * @return 오류 메시지를 포함한 Message 객체
     */
    private Message<byte[]> errorMessage(String errorMessage) {

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(errorMessage.getBytes(StandardCharsets.UTF_8),
                accessor.getMessageHeaders());
    }

    private Throwable convertThrowException(Throwable exception) {
        if (exception instanceof MessageDeliveryException) {
            return exception.getCause();
        }
        return exception;
    }
}
