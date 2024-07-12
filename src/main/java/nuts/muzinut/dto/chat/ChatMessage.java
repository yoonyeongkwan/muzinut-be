package nuts.muzinut.dto.chat;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ChatMessage {
    private Long id;
    @NotNull
    private String sender; //메시지를 보낸 사람
    @NotNull
    private String sendTo; //메시지를 받는 사람
    @NotNull
    private String message;
}
