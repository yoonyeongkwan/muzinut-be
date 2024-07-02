package nuts.muzinut.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetailBaseDto {
    private Boolean boardLikeStatus = false;
    private Boolean isBookmark = false;

    @QueryProjection
    public DetailBaseDto(Boolean boardLikeStatus, Boolean isBookmark) {
        this.boardLikeStatus = boardLikeStatus;
        this.isBookmark = isBookmark;
    }
}
