package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.AdminUploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUploadFileRepository extends JpaRepository<AdminUploadFile, Long> {
}
