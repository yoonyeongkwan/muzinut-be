package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.AdminUploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminBoardRepository extends JpaRepository<AdminBoard, Long> {

//    @Query("select count(m) from Mailbox m where m.isChecked = :isChecked and m.user = :user")

}
