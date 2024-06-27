package nuts.muzinut.domain.music;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSong is a Querydsl query type for Song
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSong extends EntityPathBase<Song> {

    private static final long serialVersionUID = 12464840L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSong song = new QSong("song");

    public final nuts.muzinut.domain.baseEntity.QBaseBoardEntity _super = new nuts.muzinut.domain.baseEntity.QBaseBoardEntity(this);

    public final QAlbum album;

    public final StringPath article = createString("article");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDt = _super.modifiedDt;

    public final StringPath musicOriginFilename = createString("musicOriginFilename");

    public final StringPath musicStoreFilename = createString("musicStoreFilename");

    public final ListPath<SongCorpArtist, QSongCorpArtist> songCorpArtists = this.<SongCorpArtist, QSongCorpArtist>createList("songCorpArtists", SongCorpArtist.class, QSongCorpArtist.class, PathInits.DIRECT2);

    public final ListPath<SongGenre, QSongGenre> songGenres = this.<SongGenre, QSongGenre>createList("songGenres", SongGenre.class, QSongGenre.class, PathInits.DIRECT2);

    //inherited
    public final StringPath title = _super.title;

    public final nuts.muzinut.domain.member.QUser user;

    //inherited
    public final NumberPath<Integer> view = _super.view;

    public QSong(String variable) {
        this(Song.class, forVariable(variable), INITS);
    }

    public QSong(Path<? extends Song> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSong(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSong(PathMetadata metadata, PathInits inits) {
        this(Song.class, metadata, inits);
    }

    public QSong(Class<? extends Song> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.album = inits.isInitialized("album") ? new QAlbum(forProperty("album")) : null;
        this.user = inits.isInitialized("user") ? new nuts.muzinut.domain.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

