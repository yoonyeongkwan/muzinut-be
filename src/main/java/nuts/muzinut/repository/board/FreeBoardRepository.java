package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

    @Query(value = "select f from FreeBoard f join fetch f.user where f.id = :id")
    Optional<FreeBoard> findFreeBoardWithUser(@Param("id") Long boardId);
}
