package nuts.muzinut.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminBoardsForm {
    private Long id;
    private String title;
    private String writer;
    private int view;
    private LocalDateTime createdDt;
}
