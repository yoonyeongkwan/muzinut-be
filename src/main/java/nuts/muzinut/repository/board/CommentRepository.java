package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.BoardType;
import nuts.muzinut.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.board = :board")
    List<Comment> findBoardComments(@Param("board") Board board);

    //Todo 게시판이 삭제 될때 해당 게시판의 댓글이 삭제되는 로직은 cascade 로 구현하지 않았기에 따로 쿼리를 작성해야함.
}
