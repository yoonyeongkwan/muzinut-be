package nuts.muzinut.dto.chat;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateChatDto {
    private Long chatRoomId;
    private String user1; //nickname
    private String user2; //nickname
}
