package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Genre;
import nuts.muzinut.domain.music.SongGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongGenreRepository extends JpaRepository<SongGenre, Long> {

    List<SongGenre> findByGenre(Genre genre);
}
