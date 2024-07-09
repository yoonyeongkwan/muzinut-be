package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.SongLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SongLikeRepository extends JpaRepository<SongLike, Long> {

    @Query("select count(sl) from SongLike sl where sl.song.id = :songId")
    Long countLikesBySongId(@Param("songId") Long songId);
}
