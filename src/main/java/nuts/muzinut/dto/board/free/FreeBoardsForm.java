package nuts.muzinut.dto.board.free;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FreeBoardsForm {
    private String title;
    private String writer;
    private LocalDateTime createdDt;
    private int like;
    private int view;
}
