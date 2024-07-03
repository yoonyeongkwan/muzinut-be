package nuts.muzinut.repository.music;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.music.Genre;
import nuts.muzinut.dto.music.SongGenreDto;
import nuts.muzinut.dto.music.SongPageDto;
import nuts.muzinut.dto.music.SongDetaillDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static nuts.muzinut.domain.member.QUser.user;
import static nuts.muzinut.domain.music.QAlbum.album;
import static nuts.muzinut.domain.music.QPlayView.playView;
import static nuts.muzinut.domain.music.QSong.song;
import static nuts.muzinut.domain.music.QSongGenre.songGenre;
import static nuts.muzinut.domain.music.QSongLike.songLike;

@RequiredArgsConstructor
public class SongRepositoryImpl implements SongRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SongPageDto> hotTOP100Song(Pageable pageable) {
        QueryResults<SongPageDto> results = queryFactory
                .select(Projections.constructor(SongPageDto.class,
                        song.id,
                        album.albumImg,
                        song.title,
                        user.nickname))
                .from(song)
                .join(song.album, album)
                .join(song.user, user)
                .where(song.id.in(JPAExpressions
                        .select(playView.song.id)
                        .from(playView)
                        .groupBy(playView.song.id)
                        .orderBy(playView.id.count().desc())
                        .limit(100)
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        // 총 음원 수는 100으로 고정
        List<SongPageDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }



    @Override
    public Page<SongPageDto> new100Song(Pageable pageable) {

        QueryResults<SongPageDto> results = queryFactory
                .select(Projections.constructor(SongPageDto.class,
                        song.id,
                        album.albumImg,
                        song.title,
                        user.nickname))
                .from(song)
                .join(song.album, album)
                .join(song.user, user)
                .where(song.id.in(
                        JPAExpressions.select(song.id)
                                .from(song)
                                .orderBy(song.createdDt.desc())
                                .limit(100)
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        // 총 음원 수는 100으로 고정
        List<SongPageDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<SongPageDto> genreSong(String genre, Pageable pageable) {
        QueryResults<SongPageDto> results = queryFactory
                .select(Projections.constructor(SongPageDto.class,
                        song.id,
                        album.albumImg,
                        song.title,
                        user.nickname))
                .from(song)
                .join(song.album, album)
                .join(song.user, user)
                .join(song.playViews, playView)
                .where(song.id.in(
                        JPAExpressions
                                .select(songGenre.song.id)
                                .from(songGenre)
                                .where(songGenre.genre.eq(Genre.valueOf(genre.toUpperCase())))
                                .limit(100)
                ))
                .groupBy(song.id)
                .orderBy(playView.id.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        // 총 음원 수는 100으로 고정
        List<SongPageDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
    // Genre( KPOP, BALLAD, POP, HIPHOP, RNB, INDIE, TROT, VIRTUBER, ETC )

    public List<SongDetaillDto> songDetail(Long id){


        return queryFactory
                .select(Projections.constructor(SongDetaillDto.class,
                        song.album.albumImg,
                        song.title,
                        song.user.nickname,
                        songLike.id.count().as("likeCount"),
                        song.lyrics,
                        song.composer,
                        song.lyricist,
                        song.album.id))
                .from(song)
                .leftJoin(song.songLikes, songLike)
                .where(song.id.eq(id))
                .groupBy(song.id)
                .fetch();
    }

    public List<SongGenreDto> songDetailGenre(Long id){

        return queryFactory
                .select(Projections.constructor(SongGenreDto.class,
                        songGenre.genre
                ))
                .from(songGenre)
                .where(songGenre.song.id.eq(id))
                .fetch();
    }

    public List<Tuple> songDetaillResult(Long id){
        return queryFactory
                .select(song,
                        JPAExpressions
                                .select(songLike.count())
                                .from(songLike)
                                .where(songLike.song.id.eq(id)))
                .from(song)
                .join(song.user, user)
                .join(song.album, album)
                .leftJoin(song.songGenres, songGenre)
                .fetchJoin()
                .where(song.id.eq(id))
                .fetch();

    }
}
