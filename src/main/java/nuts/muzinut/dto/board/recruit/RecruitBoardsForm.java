package nuts.muzinut.dto.board.recruit;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecruitBoardsForm {

    private Long id;
    private String title;
    private Long userId;
    private int view;
    private LocalDateTime createdDt;
}
