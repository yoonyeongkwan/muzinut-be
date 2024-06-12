package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.SongGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicGenreRepository extends JpaRepository<SongGenre, Long> {
}
