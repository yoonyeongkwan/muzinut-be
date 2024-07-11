package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.PlaylistMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusic, Long> {
}
