package nuts.muzinut.domain.mypick;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMypickComment is a Querydsl query type for MypickComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMypickComment extends EntityPathBase<MypickComment> {

    private static final long serialVersionUID = -925267909L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMypickComment mypickComment = new QMypickComment("mypickComment");

    public final nuts.muzinut.domain.baseEntity.QBaseTimeEntity _super = new nuts.muzinut.domain.baseEntity.QBaseTimeEntity(this);

    public final StringPath comment = createString("comment");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDt = _super.modifiedDt;

    public final nuts.muzinut.domain.member.QUser user;

    public QMypickComment(String variable) {
        this(MypickComment.class, forVariable(variable), INITS);
    }

    public QMypickComment(Path<? extends MypickComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMypickComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMypickComment(PathMetadata metadata, PathInits inits) {
        this(MypickComment.class, metadata, inits);
    }

    public QMypickComment(Class<? extends MypickComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new nuts.muzinut.domain.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

