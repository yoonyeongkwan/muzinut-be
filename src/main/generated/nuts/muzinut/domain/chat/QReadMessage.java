package nuts.muzinut.domain.chat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReadMessage is a Querydsl query type for ReadMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReadMessage extends EntityPathBase<ReadMessage> {

    private static final long serialVersionUID = -228274235L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReadMessage readMessage = new QReadMessage("readMessage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final QMessage message;

    public QReadMessage(String variable) {
        this(ReadMessage.class, forVariable(variable), INITS);
    }

    public QReadMessage(Path<? extends ReadMessage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReadMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReadMessage(PathMetadata metadata, PathInits inits) {
        this(ReadMessage.class, metadata, inits);
    }

    public QReadMessage(Class<? extends ReadMessage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.message = inits.isInitialized("message") ? new QMessage(forProperty("message"), inits.get("message")) : null;
    }

}

