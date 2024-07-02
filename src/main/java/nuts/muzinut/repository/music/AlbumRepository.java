package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Album;
import nuts.muzinut.dto.music.AlbumDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findByName(String name);

    Optional<Album> findByAlbumImg(String filename);

    List<Album> findByUserId(Long userId);
}

