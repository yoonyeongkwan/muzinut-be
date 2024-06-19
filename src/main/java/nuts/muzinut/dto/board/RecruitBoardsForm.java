package nuts.muzinut.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class RecruitBoardsForm {

    private Long id;
    private String title;
    private Long userId;
    private int view;
    private LocalDateTime createdDt;
}
