package nuts.muzinut.dto.board.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardsForm {
    private Long id;
    private String title;
    private String writer;
    private int view;
    private int like;
    private LocalDateTime createdDt;

    @QueryProjection
    public BoardsForm(Long id, String title, String writer, int view, int like, LocalDateTime createdDt) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.view = view;
        this.like = like;
        this.createdDt = createdDt;
    }
}
