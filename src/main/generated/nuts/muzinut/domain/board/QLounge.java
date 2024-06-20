package nuts.muzinut.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLounge is a Querydsl query type for Lounge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLounge extends EntityPathBase<Lounge> {

    private static final long serialVersionUID = 1563352652L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLounge lounge = new QLounge("lounge");

    public final QBoard _super = new QBoard(this);

    //inherited
    public final ListPath<Comment, QComment> comments = _super.comments;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final ListPath<Like, QLike> likes = _super.likes;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDt = _super.modifiedDt;

    //inherited
    public final StringPath title = _super.title;

    public final nuts.muzinut.domain.member.QUser user;

    //inherited
    public final NumberPath<Integer> view = _super.view;

    public QLounge(String variable) {
        this(Lounge.class, forVariable(variable), INITS);
    }

    public QLounge(Path<? extends Lounge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLounge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLounge(PathMetadata metadata, PathInits inits) {
        this(Lounge.class, metadata, inits);
    }

    public QLounge(Class<? extends Lounge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new nuts.muzinut.domain.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

