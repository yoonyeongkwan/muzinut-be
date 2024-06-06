package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
