package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.BoardType;
import nuts.muzinut.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.user join fetch c.board where c.board = :board")
    List<Comment> findBoardComments(@Param("board") Board board);

    @Modifying
    @Query("delete from Comment c where c.board = :board")
    void deleteCommentsByBoardId(@Param("board") Board board);

    @Modifying
    @Query("delete from Comment c where c.id = :commentId")
    void deleteByCommentId(@Param("commentId") Long commentId);
}
