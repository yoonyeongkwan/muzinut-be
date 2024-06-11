package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.AdminBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminBoardRepository extends JpaRepository<AdminBoard, Long> {
}
