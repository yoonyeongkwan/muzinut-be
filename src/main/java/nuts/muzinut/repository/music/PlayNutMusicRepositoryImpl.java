package nuts.muzinut.repository.music;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.music.PlayNutMusicDto;
import nuts.muzinut.dto.music.SongPageDto;

import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.orderBy;
import static nuts.muzinut.domain.music.QPlayNut.playNut;
import static nuts.muzinut.domain.music.QPlayNutMusic.playNutMusic;
import static nuts.muzinut.domain.music.QSong.song;
import static nuts.muzinut.domain.music.QAlbum.album;
import static nuts.muzinut.domain.member.QUser.user;
import static org.hibernate.query.results.Builders.fetch;

@RequiredArgsConstructor
public class PlayNutMusicRepositoryImpl implements PlayNutMusicRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PlayNutMusicDto> findPlayNutMusic(Long playNutId) {

        return queryFactory
                .select(Projections.constructor(PlayNutMusicDto.class,
                        playNutMusic.id,
                        song.id,
                        album.albumImg,
                        song.title,
                        user.nickname))
                .from(song)
                .join(song.album, album)
                .join(song.user, user)
                .leftJoin(playNutMusic)
                .on(song.id.eq(playNutMusic.song.id))
                .where(playNutMusic.playNut.id.eq(playNutId))
                .fetch();

    }
}
