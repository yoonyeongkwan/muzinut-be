package nuts.muzinut.domain.music;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSongGenre is a Querydsl query type for SongGenre
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSongGenre extends EntityPathBase<SongGenre> {

    private static final long serialVersionUID = 1907516315L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSongGenre songGenre = new QSongGenre("songGenre");

    public final EnumPath<Genre> genre = createEnum("genre", Genre.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSong song;

    public QSongGenre(String variable) {
        this(SongGenre.class, forVariable(variable), INITS);
    }

    public QSongGenre(Path<? extends SongGenre> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSongGenre(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSongGenre(PathMetadata metadata, PathInits inits) {
        this(SongGenre.class, metadata, inits);
    }

    public QSongGenre(Class<? extends SongGenre> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.song = inits.isInitialized("song") ? new QSong(forProperty("song"), inits.get("song")) : null;
    }

}

