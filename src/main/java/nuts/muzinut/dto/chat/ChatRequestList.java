package nuts.muzinut.dto.chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRequestList {
    private Long id;
    private String nickname;
    private String profileImg;
    private String message;
}
