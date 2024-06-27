package nuts.muzinut.dto.board.comment;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ReplyDto {
    private Long id;
    private String content;
    private String replyWriter;
    private Long commentId;
    private LocalDateTime createdDt;
    private String profileImgFilename; // 프로필 이미지 파일명 추가

    @QueryProjection
    public ReplyDto(Long id, String content, String replyWriter, Long commentId, LocalDateTime createdDt) {
        this.id = id;
        this.content = content;
        this.replyWriter = replyWriter;
        this.commentId = commentId;
        this.createdDt = createdDt;
    }

    public ReplyDto(Long id, String content, String replyWriter, LocalDateTime createdDt) {
        this.id = id;
        this.content = content;
        this.replyWriter = replyWriter;
        this.createdDt = createdDt;
    }
}
