package nuts.muzinut.dto.board.recruit;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class SaveRecruitBoardDto {

    private String title;
    private String content;
    private int view;
    private int recruitMember;
    private LocalDateTime startDuration;
    private LocalDateTime endDuration;
    private LocalDateTime startWorkDuration;
    private LocalDateTime endWorkDuration;
    private List<String> genres;
}
