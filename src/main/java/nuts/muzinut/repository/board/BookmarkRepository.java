package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
