package nuts.muzinut.dto.board.recruit;

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
    private List like;  // 좋아요 수 추가
}
