package nuts.muzinut.domain.music;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayView is a Querydsl query type for PlayView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayView extends EntityPathBase<PlayView> {

    private static final long serialVersionUID = 1375251500L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayView playView = new QPlayView("playView");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> playDt = createDate("playDt", java.time.LocalDate.class);

    public final QSong song;

    public QPlayView(String variable) {
        this(PlayView.class, forVariable(variable), INITS);
    }

    public QPlayView(Path<? extends PlayView> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayView(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayView(PathMetadata metadata, PathInits inits) {
        this(PlayView.class, metadata, inits);
    }

    public QPlayView(Class<? extends PlayView> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.song = inits.isInitialized("song") ? new QSong(forProperty("song"), inits.get("song")) : null;
    }

}

