package nuts.muzinut.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ChatRequestListDto {
    List<ChatRequestList> requestList = new ArrayList<>();
}
