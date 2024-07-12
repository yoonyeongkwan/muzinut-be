package nuts.muzinut.dto.chat;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MessagesDto {
    private List<Messages> messages = new ArrayList<>();
    private Map<String, String> test = new HashMap<>();
}
