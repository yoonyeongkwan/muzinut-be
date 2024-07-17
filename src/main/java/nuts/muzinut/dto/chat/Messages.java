package nuts.muzinut.dto.chat;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Messages {
    private Long id;
    private String nickname;
    private LocalDateTime sendTime;
    private String message;
    private String profileImg;
}
