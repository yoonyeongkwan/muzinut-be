package nuts.muzinut.dto.board.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventBoardsForm {
    private Long id;
    private String title;
    private String writer;
    private LocalDateTime createdDt;
    private int like;
    private int view;
}
