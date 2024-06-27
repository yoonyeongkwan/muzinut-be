package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Album;
import nuts.muzinut.domain.music.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long>, SongRepositoryCustom {

    List<Song> findAllByAlbum(Album album);

    Optional<Song> findByFileName(String fileName);

//    @Query("SELECT NEW nuts.muzinut.dto.album.SongPageDto(s.id, a.albumImg, s.title, u.nickname) " +
//            "FROM Song s " +
//            "JOIN s.album a " +
//            "JOIN s.user u " +
//            "ORDER BY s.createdDt DESC")
//    Page<SongPageDto> findNewSong(Pageable pageable);


}
