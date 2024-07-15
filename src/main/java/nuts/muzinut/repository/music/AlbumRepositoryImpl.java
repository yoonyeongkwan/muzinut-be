package nuts.muzinut.repository.music;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.music.AlbumDetaillDto;
import nuts.muzinut.dto.music.AlbumSongDetaillDto;

import java.util.List;

import static nuts.muzinut.domain.member.QUser.user;
import static nuts.muzinut.domain.music.QAlbum.album;
import static nuts.muzinut.domain.music.QSong.song;

@RequiredArgsConstructor
public class AlbumRepositoryImpl implements AlbumRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    // 앨범 상세 페이지 불러오는 쿼리
    @Override
    public List<AlbumDetaillDto> albumDetail(Long id) {
        return queryFactory
                .select(Projections.constructor(AlbumDetaillDto.class,
                        album.name,
                        album.albumImg,
                        user.nickname,
                        album.intro
                ))
                .from(album)
                .where(album.id.eq(id))
                .groupBy(album.id)
                .fetch();
    }

    // 앨범에 수록된 곡들을 불러오는 쿼리
    @Override
    public List<AlbumSongDetaillDto> albumSongDetail(Long id) {
        return queryFactory
                .select(Projections.constructor(AlbumSongDetaillDto.class,
                        song.id,
                        song.title,
                        user.nickname
                ))
                .from(song)
                .where(song.album.id.eq(id))
                .groupBy(song.id)
                .fetch();
    }
}
