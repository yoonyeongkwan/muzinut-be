package nuts.muzinut.dto.board.recruit;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecruitBoardsForm {

    private Long id;
    private String title;
    private String nickname;
    private int view;
    private LocalDateTime createdDt;
    private long likeCount;  // 좋아요 수 추가
}
