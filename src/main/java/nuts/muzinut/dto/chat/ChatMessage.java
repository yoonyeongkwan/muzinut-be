package nuts.muzinut.dto.chat;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ChatMessage {
    private Long id;
    private String sender; //메시지를 보낸 사람
    private String sendTo; //메시지를 받는 사람
    private String message;
}
