package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.EventBoard;
import nuts.muzinut.domain.board.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventBoardRepository extends JpaRepository<EventBoard, Long> {

    @Query(value = "select e from EventBoard e join fetch e.user where e.id = :id")
    Optional<EventBoard> findEventBoardWithUser(@Param("id") Long boardId);

    @Modifying
    @Query("update EventBoard e set e.filename = :filename, e.title = :title, e.img = :img " +
            "where e.id = :id")
    void updateEventBoard(@Param("filename") String filename, @Param("title") String title,
                          @Param("img") String img ,@Param("id") Long id);

}
