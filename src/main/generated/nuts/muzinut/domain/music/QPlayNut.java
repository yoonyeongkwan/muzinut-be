package nuts.muzinut.domain.music;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayNut is a Querydsl query type for PlayNut
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayNut extends EntityPathBase<PlayNut> {

    private static final long serialVersionUID = -786928346L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayNut playNut = new QPlayNut("playNut");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<PlayNutMusic, QPlayNutMusic> playNutMusics = this.<PlayNutMusic, QPlayNutMusic>createList("playNutMusics", PlayNutMusic.class, QPlayNutMusic.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final nuts.muzinut.domain.member.QUser user;

    public QPlayNut(String variable) {
        this(PlayNut.class, forVariable(variable), INITS);
    }

    public QPlayNut(Path<? extends PlayNut> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayNut(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayNut(PathMetadata metadata, PathInits inits) {
        this(PlayNut.class, metadata, inits);
    }

    public QPlayNut(Class<? extends PlayNut> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new nuts.muzinut.domain.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

