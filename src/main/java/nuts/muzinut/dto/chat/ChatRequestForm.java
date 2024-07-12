package nuts.muzinut.dto.chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatRequestForm {
    @NotNull
    private String nickname;

    @NotNull
    private String message;
}
