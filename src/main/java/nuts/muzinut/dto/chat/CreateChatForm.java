package nuts.muzinut.dto.chat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateChatForm {
    @NotNull
    private String sendTo;  //메시지를 보낼 상대방의 닉네임
    @NotNull
    private String message; //보낼 메시지
}
