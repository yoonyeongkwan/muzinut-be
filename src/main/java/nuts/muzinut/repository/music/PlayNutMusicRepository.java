package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.PlayNutMusic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayNutMusicRepository extends JpaRepository<PlayNutMusic, Long> {
}
