package nuts.muzinut.repository.music;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static nuts.muzinut.domain.music.QSong.song;
import static nuts.muzinut.domain.music.QSongGenre.songGenre;

@RequiredArgsConstructor
@Repository
public class SongDetailRepository {
    private final JPAQueryFactory queryFactory;

    public List<Tuple> getSongDetail(Long songId) {
        return queryFactory
                .select(
                        song.title,
                        song.lyrics,
                        song.lyricist,
                        song.composer,
                        song.album.albumImg,
                        song.user.nickname.as("artist"),
                        songGenre.genre
                )
                .from(song)
                .leftJoin(songGenre)
                .fetch();
    }
}
