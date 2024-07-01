package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.EventBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventBoardRepository extends JpaRepository<EventBoard, Long> {

//    @Query("select e from EventBoard e join fetch e.like")
}
