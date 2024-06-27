package nuts.muzinut.dto.board.comment;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.board.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private String commentWriter;
    private LocalDateTime createdDt;

    private List<ReplyDto> replies = new ArrayList<>();

    private String profileImgFilename; // 프로필 이미지 파일명 추가

    @QueryProjection
    public CommentDto(Long id, String content, String commentWriter, LocalDateTime createdDt) {
        this.id = id;
        this.content = content;
        this.commentWriter = commentWriter;
        this.createdDt = createdDt;
    }
}
