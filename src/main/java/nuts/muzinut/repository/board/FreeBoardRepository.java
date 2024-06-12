package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

}
