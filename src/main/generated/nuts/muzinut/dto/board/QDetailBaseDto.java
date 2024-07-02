package nuts.muzinut.dto.board;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * nuts.muzinut.dto.board.QDetailBaseDto is a Querydsl Projection type for DetailBaseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDetailBaseDto extends ConstructorExpression<DetailBaseDto> {

    private static final long serialVersionUID = 115783686L;

    public QDetailBaseDto(com.querydsl.core.types.Expression<Boolean> boardLikeStatus, com.querydsl.core.types.Expression<Boolean> isBookmark) {
        super(DetailBaseDto.class, new Class<?>[]{boolean.class, boolean.class}, boardLikeStatus, isBookmark);
    }

}

