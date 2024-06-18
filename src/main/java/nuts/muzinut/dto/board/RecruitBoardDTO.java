package nuts.muzinut.dto.board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecruitBoardDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDt;
    private LocalDateTime modifiedDt;
    private int view;
    private int recruitMember;
    private LocalDateTime startDuration;
    private LocalDateTime endDuration;
    private LocalDateTime startWorkDuration;
    private LocalDateTime endWorkDuration;
    private List<String> genres;
    private Long userId;
}
