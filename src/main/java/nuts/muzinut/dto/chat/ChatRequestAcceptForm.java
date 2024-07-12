package nuts.muzinut.dto.chat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatRequestAcceptForm {
    @NotNull
    private String nickname;

    @NotNull
    private String message;
}
