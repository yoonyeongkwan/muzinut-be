package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
