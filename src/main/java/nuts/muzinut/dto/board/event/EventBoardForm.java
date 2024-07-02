package nuts.muzinut.dto.board.event;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventBoardForm {

    @NotNull
    private String title;
}
