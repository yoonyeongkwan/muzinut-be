package nuts.muzinut.domain.music;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaylistMusic is a Querydsl query type for PlaylistMusic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaylistMusic extends EntityPathBase<PlaylistMusic> {

    private static final long serialVersionUID = -1753855200L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaylistMusic playlistMusic = new QPlaylistMusic("playlistMusic");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> musicId = createNumber("musicId", Long.class);

    public final QPlaylist playlist;

    public QPlaylistMusic(String variable) {
        this(PlaylistMusic.class, forVariable(variable), INITS);
    }

    public QPlaylistMusic(Path<? extends PlaylistMusic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaylistMusic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaylistMusic(PathMetadata metadata, PathInits inits) {
        this(PlaylistMusic.class, metadata, inits);
    }

    public QPlaylistMusic(Class<? extends PlaylistMusic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.playlist = inits.isInitialized("playlist") ? new QPlaylist(forProperty("playlist"), inits.get("playlist")) : null;
    }

}

