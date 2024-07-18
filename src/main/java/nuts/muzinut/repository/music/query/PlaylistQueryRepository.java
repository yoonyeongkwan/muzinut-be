package nuts.muzinut.repository.music.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.dto.music.playlist.PlaylistMusicsDto;
import org.springframework.stereotype.Repository;

import java.util.List;

import static nuts.muzinut.domain.member.QUser.user;
import static nuts.muzinut.domain.music.QAlbum.album;
import static nuts.muzinut.domain.music.QSong.song;
import static nuts.muzinut.domain.music.QPlaylistMusic.playlistMusic;

@RequiredArgsConstructor
@Repository
public class PlaylistQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<PlaylistMusicsDto> getPlaylistMusics(Long userId) {
        return queryFactory
                .select(Projections.constructor(PlaylistMusicsDto.class,
                        playlistMusic.id,
                        song.id,
                        song.title,
                        user.nickname,
                        album.albumImg
                ))
                .from(playlistMusic)
                .join(playlistMusic.song, song)
                .join(song.user, user)
                .join(song.album, album)
                .where(playlistMusic.playlist.user.id.eq(userId))
                .fetch();
    }

    public List<Long> getStorePlaylistMusicsCount(Playlist playlist) {
        return queryFactory
                .select(
                        playlistMusic.count()
                )
                .from(playlistMusic)
                .join(playlistMusic.playlist)
                .where(playlistMusic.playlist.eq(playlist))
                .fetch();
    }
}
