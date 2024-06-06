package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.PlaylistMusic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusic, Long> {
}
