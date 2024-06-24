package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.AdminUploadFile;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdminBoardRepository extends JpaRepository<AdminBoard, Long> {
    void deleteById(Long id);

    @Query("select a from AdminBoard a join fetch a.user where a.id = :id")
    Optional<AdminBoard> findAdminBoardWithUser(@Param("id") Long boardId);
}
