package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Bookmark;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    // 특정 사용자가 특정 게시판에 북마크를 눌렀는지 확인하는 메서드
    boolean existsByUserAndBoard(User user, Board board);

    // 특정 사용자가 한 북마크를 삭제하는 메서드
    @Modifying
    @Transactional
    @Query("delete from Bookmark b where b.user = :user and b.board.id = :id")
    void deleteByUserAndBoard(@Param("user") User user, @Param("id") Long id);

}
