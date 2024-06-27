package nuts.muzinut.dto.board.recruit;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecruitBoardForm {

    private String title;
    private String content;
    private int recruitMember;
    private LocalDateTime startDuration;
    private LocalDateTime endDuration;
    private LocalDateTime startWorkDuration;
    private LocalDateTime endWorkDuration;
    private List<String> genres;
}
