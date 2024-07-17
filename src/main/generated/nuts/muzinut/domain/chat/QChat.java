package nuts.muzinut.domain.chat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChat is a Querydsl query type for Chat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChat extends EntityPathBase<Chat> {

    private static final long serialVersionUID = -200671324L;

    public static final QChat chat = new QChat("chat");

    public final ListPath<ChatMember, QChatMember> chatMembers = this.<ChatMember, QChatMember>createList("chatMembers", ChatMember.class, QChatMember.class, PathInits.DIRECT2);

    public final EnumPath<ChatStatus> chatStatus = createEnum("chatStatus", ChatStatus.class);

    public final DateTimePath<java.time.LocalDateTime> created_dt = createDateTime("created_dt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Message, QMessage> messages = this.<Message, QMessage>createList("messages", Message.class, QMessage.class, PathInits.DIRECT2);

    public QChat(String variable) {
        super(Chat.class, forVariable(variable));
    }

    public QChat(Path<? extends Chat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChat(PathMetadata metadata) {
        super(Chat.class, metadata);
    }

}

