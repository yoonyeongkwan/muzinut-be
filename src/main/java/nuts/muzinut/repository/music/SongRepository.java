package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Album;
import nuts.muzinut.domain.music.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long>, SongRepositoryCustom {

    List<Song> findAllByAlbum(Album album);

    Optional<Song> findByFileName(String fileName);

    @Modifying
    @Transactional
    @Query("update Song s set s.title = :title, s.lyricist = :lyricist, "
    + "s.composer = :composer, s.lyrics = :lyrics, s.fileName = :fileName "
    + "where s.id = :id")
    void updateById(@Param("title") String title, @Param("lyricist") String lyricist,
                    @Param("composer") String composer, @Param("lyrics") String lyrics,
                    @Param("fileName") String fileName, @Param("id") Long id);

}
