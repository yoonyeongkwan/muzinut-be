package nuts.muzinut.dto.board.recruit;

import lombok.AllArgsConstructor;
import lombok.Data;
import nuts.muzinut.dto.board.comment.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class DetailRecruitBoardDto {

    private String title;
    private String content;
    private int view;
    private int recruitMember;
    private LocalDateTime startDuration;
    private LocalDateTime endDuration;
    private LocalDateTime startWorkDuration;
    private LocalDateTime endWorkDuration;
    private List<String> genres;
    private String author; // 작성자 정보 추가
    private List<CommentDto> comments; // 댓글 리스트 추가
}