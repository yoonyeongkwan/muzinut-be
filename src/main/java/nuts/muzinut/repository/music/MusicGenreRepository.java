package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicGenreRepository extends JpaRepository<MusicGenre, Long> {
}
