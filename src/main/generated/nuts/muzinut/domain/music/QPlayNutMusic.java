package nuts.muzinut.domain.music;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayNutMusic is a Querydsl query type for PlayNutMusic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayNutMusic extends EntityPathBase<PlayNutMusic> {

    private static final long serialVersionUID = 1668274335L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayNutMusic playNutMusic = new QPlayNutMusic("playNutMusic");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPlayNut playNut;

    public final NumberPath<Long> songId = createNumber("songId", Long.class);

    public QPlayNutMusic(String variable) {
        this(PlayNutMusic.class, forVariable(variable), INITS);
    }

    public QPlayNutMusic(Path<? extends PlayNutMusic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayNutMusic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayNutMusic(PathMetadata metadata, PathInits inits) {
        this(PlayNutMusic.class, metadata, inits);
    }

    public QPlayNutMusic(Class<? extends PlayNutMusic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.playNut = inits.isInitialized("playNut") ? new QPlayNut(forProperty("playNut"), inits.get("playNut")) : null;
    }

}

