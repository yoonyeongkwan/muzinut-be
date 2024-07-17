package nuts.muzinut.repository.mypick;

import nuts.muzinut.domain.mypick.MypickComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MypickCommentRepository extends JpaRepository<MypickComment, Long> {
}
