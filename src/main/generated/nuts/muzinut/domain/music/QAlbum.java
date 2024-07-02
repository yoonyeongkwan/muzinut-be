package nuts.muzinut.domain.music;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbum is a Querydsl query type for Album
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbum extends EntityPathBase<Album> {

    private static final long serialVersionUID = 369686300L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbum album = new QAlbum("album");

    public final StringPath albumImg = createString("albumImg");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final StringPath name = createString("name");

    public final ListPath<Song, QSong> songList = this.<Song, QSong>createList("songList", Song.class, QSong.class, PathInits.DIRECT2);

    public final nuts.muzinut.domain.member.QUser user;

    public QAlbum(String variable) {
        this(Album.class, forVariable(variable), INITS);
    }

    public QAlbum(Path<? extends Album> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbum(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbum(PathMetadata metadata, PathInits inits) {
        this(Album.class, metadata, inits);
    }

    public QAlbum(Class<? extends Album> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new nuts.muzinut.domain.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

