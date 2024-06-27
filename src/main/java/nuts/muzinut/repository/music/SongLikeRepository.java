package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.SongLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongLikeRepository extends JpaRepository<SongLike, Long> {
}
