package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Genre;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.domain.music.SongGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SongGenreRepository extends JpaRepository<SongGenre, Long> {

    List<SongGenre> findByGenre(Genre genre);

    List<SongGenre> findBySong(Song song);

    @Modifying
    @Transactional
    @Query("update SongGenre sg set sg.genre = :genre where sg.id = :id")
    void updateById(@Param("genre") Genre genre, @Param("id") Long id);
}
