package nuts.muzinut.domain.music;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSongCorpArtist is a Querydsl query type for SongCorpArtist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSongCorpArtist extends EntityPathBase<SongCorpArtist> {

    private static final long serialVersionUID = -1105213319L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSongCorpArtist songCorpArtist = new QSongCorpArtist("songCorpArtist");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final QSong song;

    public QSongCorpArtist(String variable) {
        this(SongCorpArtist.class, forVariable(variable), INITS);
    }

    public QSongCorpArtist(Path<? extends SongCorpArtist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSongCorpArtist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSongCorpArtist(PathMetadata metadata, PathInits inits) {
        this(SongCorpArtist.class, metadata, inits);
    }

    public QSongCorpArtist(Class<? extends SongCorpArtist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.song = inits.isInitialized("song") ? new QSong(forProperty("song"), inits.get("song")) : null;
    }

}

