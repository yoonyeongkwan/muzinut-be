package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    @Query("select p from Playlist p join fetch p.user u where u.id = :userId")
    Optional<Playlist> findByUserId(@Param("userId") Long userId);
}
