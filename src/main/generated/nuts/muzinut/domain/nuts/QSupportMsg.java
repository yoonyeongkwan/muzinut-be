package nuts.muzinut.domain.nuts;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSupportMsg is a Querydsl query type for SupportMsg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSupportMsg extends EntityPathBase<SupportMsg> {

    private static final long serialVersionUID = 353702032L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSupportMsg supportMsg = new QSupportMsg("supportMsg");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final NumberPath<Long> sponsorId = createNumber("sponsorId", Long.class);

    public final nuts.muzinut.domain.member.QUser user;

    public QSupportMsg(String variable) {
        this(SupportMsg.class, forVariable(variable), INITS);
    }

    public QSupportMsg(Path<? extends SupportMsg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSupportMsg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSupportMsg(PathMetadata metadata, PathInits inits) {
        this(SupportMsg.class, metadata, inits);
    }

    public QSupportMsg(Class<? extends SupportMsg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new nuts.muzinut.domain.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

