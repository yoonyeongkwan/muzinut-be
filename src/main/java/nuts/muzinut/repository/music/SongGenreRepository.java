package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.SongGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongGenreRepository extends JpaRepository<SongGenre, Long> {
}
