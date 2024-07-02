package nuts.muzinut.repository.music;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.music.Genre;
import nuts.muzinut.domain.music.SongGenre;
import nuts.muzinut.dto.music.SongPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.data.domain.Pageable;
import java.util.List;

import static nuts.muzinut.domain.member.QUser.user;
import static nuts.muzinut.domain.music.QAlbum.album;
import static nuts.muzinut.domain.music.QPlayView.playView;
import static nuts.muzinut.domain.music.QSong.song;
import static nuts.muzinut.domain.music.QSongGenre.songGenre;

@RequiredArgsConstructor
public class SongRepositoryImpl implements SongRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SongPageDto> hotTOP100Song(Pageable pageable) {
        List<SongPageDto> content = queryFactory
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
                .fetch();

        // 총 음원 수는 100으로 고정
        long total = 100;

        return new PageImpl<>(content, pageable, total);
    }



    @Override
    public Page<SongPageDto> new100Song(Pageable pageable) {

        List<SongPageDto> content = queryFactory
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
                .fetch();

        // 총 음원 수는 100으로 고정
        long total = 100;

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<SongPageDto> genreSong(String genre, Pageable pageable) {
        List<SongPageDto> content = queryFactory
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
                .fetch();


        // 총 음원 수는 100으로 고정
        long total = 100;

        return new PageImpl<>(content, pageable, total);
    }
    // Genre( KPOP, BALLAD, POP, HIPHOP, RNB, INDIE, TROT, VIRTUBER, ETC )

}
