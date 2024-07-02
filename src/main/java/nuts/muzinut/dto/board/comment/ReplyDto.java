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
    private String replyProfileImg; //대댓글을 작성한 사람의 프로필 이미지
    private LocalDateTime createdDt;
    private String profileImgFilename; // 프로필 이미지 파일명 추가
    private Long commentId;

    @QueryProjection
    public ReplyDto(Long id, String content, String replyWriter, Long commentId,
                    LocalDateTime createdDt, String replyProfileImg) {
        this.id = id;
        this.content = content;
        this.replyProfileImg = replyProfileImg;
        this.replyWriter = replyWriter;
        this.createdDt = createdDt;
    }

    public ReplyDto(Long id, String content, String replyWriter, LocalDateTime createdDt, String replyProfileImg) {
        this.id = id;
        this.content = content;
        this.replyWriter = replyWriter;
        this.createdDt = createdDt;
        this.replyProfileImg = replyProfileImg;
    }
}
