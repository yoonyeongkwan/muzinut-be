package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

    @Query(value = "select f from FreeBoard f join fetch f.user where f.id = :id")
    Optional<FreeBoard> findFreeBoardWithUser(@Param("id") Long boardId);

    @Modifying
    @Query("update FreeBoard f set f.filename = :filename, f.title = :title " +
            "where f.id = :id")
    void updateFreeBoard(@Param("filename") String filename, @Param("title") String title,
                                        @Param("id") Long id);
}
