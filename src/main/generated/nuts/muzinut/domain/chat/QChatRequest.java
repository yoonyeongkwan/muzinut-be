package nuts.muzinut.domain.chat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRequest is a Querydsl query type for ChatRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRequest extends EntityPathBase<ChatRequest> {

    private static final long serialVersionUID = -1672385973L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRequest chatRequest = new QChatRequest("chatRequest");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isReceived = createBoolean("isReceived");

    public final StringPath message = createString("message");

    public final nuts.muzinut.domain.member.QUser receiveUser;

    public final nuts.muzinut.domain.member.QUser requestUser;

    public final DateTimePath<java.time.LocalDateTime> sendTime = createDateTime("sendTime", java.time.LocalDateTime.class);

    public QChatRequest(String variable) {
        this(ChatRequest.class, forVariable(variable), INITS);
    }

    public QChatRequest(Path<? extends ChatRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRequest(PathMetadata metadata, PathInits inits) {
        this(ChatRequest.class, metadata, inits);
    }

    public QChatRequest(Class<? extends ChatRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiveUser = inits.isInitialized("receiveUser") ? new nuts.muzinut.domain.member.QUser(forProperty("receiveUser"), inits.get("receiveUser")) : null;
        this.requestUser = inits.isInitialized("requestUser") ? new nuts.muzinut.domain.member.QUser(forProperty("requestUser"), inits.get("requestUser")) : null;
    }

}

