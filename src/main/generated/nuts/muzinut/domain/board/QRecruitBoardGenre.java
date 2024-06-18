package nuts.muzinut.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitBoardGenre is a Querydsl query type for RecruitBoardGenre
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitBoardGenre extends EntityPathBase<RecruitBoardGenre> {

    private static final long serialVersionUID = 1514932169L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitBoardGenre recruitBoardGenre = new QRecruitBoardGenre("recruitBoardGenre");

    public final StringPath genre = createString("genre");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRecruitBoard recruitBoard;

    public QRecruitBoardGenre(String variable) {
        this(RecruitBoardGenre.class, forVariable(variable), INITS);
    }

    public QRecruitBoardGenre(Path<? extends RecruitBoardGenre> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitBoardGenre(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitBoardGenre(PathMetadata metadata, PathInits inits) {
        this(RecruitBoardGenre.class, metadata, inits);
    }

    public QRecruitBoardGenre(Class<? extends RecruitBoardGenre> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recruitBoard = inits.isInitialized("recruitBoard") ? new QRecruitBoard(forProperty("recruitBoard"), inits.get("recruitBoard")) : null;
    }

}

