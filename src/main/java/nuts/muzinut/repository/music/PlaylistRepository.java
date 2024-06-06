package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
