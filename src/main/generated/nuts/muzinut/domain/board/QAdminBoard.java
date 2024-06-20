package nuts.muzinut.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdminBoard is a Querydsl query type for AdminBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminBoard extends EntityPathBase<AdminBoard> {

    private static final long serialVersionUID = 928402665L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdminBoard adminBoard = new QAdminBoard("adminBoard");

    public final QBoard _super = new QBoard(this);

    public final ListPath<AdminUploadFile, QAdminUploadFile> adminUploadFiles = this.<AdminUploadFile, QAdminUploadFile>createList("adminUploadFiles", AdminUploadFile.class, QAdminUploadFile.class, PathInits.DIRECT2);

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<Like, QLike> likes = this.<Like, QLike>createList("likes", Like.class, QLike.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDt = _super.modifiedDt;

    //inherited
    public final StringPath title = _super.title;

    public final nuts.muzinut.domain.member.QUser user;

    //inherited
    public final NumberPath<Integer> view = _super.view;

    public QAdminBoard(String variable) {
        this(AdminBoard.class, forVariable(variable), INITS);
    }

    public QAdminBoard(Path<? extends AdminBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdminBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdminBoard(PathMetadata metadata, PathInits inits) {
        this(AdminBoard.class, metadata, inits);
    }

    public QAdminBoard(Class<? extends AdminBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new nuts.muzinut.domain.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

