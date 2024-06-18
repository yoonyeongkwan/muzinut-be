package nuts.muzinut.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitBoard is a Querydsl query type for RecruitBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitBoard extends EntityPathBase<RecruitBoard> {

    private static final long serialVersionUID = 1799236058L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitBoard recruitBoard = new QRecruitBoard("recruitBoard");

    public final nuts.muzinut.domain.baseEntity.QBaseBoardEntity _super = new nuts.muzinut.domain.baseEntity.QBaseBoardEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final DateTimePath<java.time.LocalDateTime> endDuration = createDateTime("endDuration", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> endWorkDuration = createDateTime("endWorkDuration", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDt = _super.modifiedDt;

    public final ListPath<RecruitBoardGenre, QRecruitBoardGenre> recruitBoardGenres = this.<RecruitBoardGenre, QRecruitBoardGenre>createList("recruitBoardGenres", RecruitBoardGenre.class, QRecruitBoardGenre.class, PathInits.DIRECT2);

    public final NumberPath<Integer> recruitMember = createNumber("recruitMember", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> startDuration = createDateTime("startDuration", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startWorkDuration = createDateTime("startWorkDuration", java.time.LocalDateTime.class);

    //inherited
    public final StringPath title = _super.title;

    public final nuts.muzinut.domain.member.QUser user;

    //inherited
    public final NumberPath<Integer> view = _super.view;

    public QRecruitBoard(String variable) {
        this(RecruitBoard.class, forVariable(variable), INITS);
    }

    public QRecruitBoard(Path<? extends RecruitBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitBoard(PathMetadata metadata, PathInits inits) {
        this(RecruitBoard.class, metadata, inits);
    }

    public QRecruitBoard(Class<? extends RecruitBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new nuts.muzinut.domain.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

