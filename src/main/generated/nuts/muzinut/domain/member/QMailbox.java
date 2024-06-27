package nuts.muzinut.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMailbox is a Querydsl query type for Mailbox
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMailbox extends EntityPathBase<Mailbox> {

    private static final long serialVersionUID = 1065167786L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMailbox mailbox = new QMailbox("mailbox");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isChecked = createBoolean("isChecked");

    public final StringPath message = createString("message");

    public final QUser user;

    public QMailbox(String variable) {
        this(Mailbox.class, forVariable(variable), INITS);
    }

    public QMailbox(Path<? extends Mailbox> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMailbox(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMailbox(PathMetadata metadata, PathInits inits) {
        this(Mailbox.class, metadata, inits);
    }

    public QMailbox(Class<? extends Mailbox> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

