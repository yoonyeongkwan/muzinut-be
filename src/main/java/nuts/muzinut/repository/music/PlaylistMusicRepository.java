package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.domain.music.PlaylistMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusic, Long> {
    @Modifying
    @Query(value = "delete from PlaylistMusic p where p.playlist = :playlist")
    void deleteAllPlaylistMusic(@Param("playlist") Playlist playlist);
}
