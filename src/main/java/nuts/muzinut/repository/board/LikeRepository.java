package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.BoardType;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

}
