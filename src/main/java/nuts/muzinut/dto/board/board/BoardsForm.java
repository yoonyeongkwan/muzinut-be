package nuts.muzinut.dto.board.board;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardsForm {
    private Long id;
    private String title;
    private String writer;
    private int view;
    private int like;
    private LocalDateTime createdDt;
}
