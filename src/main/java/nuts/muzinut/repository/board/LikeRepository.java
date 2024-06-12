package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
