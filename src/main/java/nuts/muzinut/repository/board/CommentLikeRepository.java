package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.CommentLike;
import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    // 특정 사용자가 특정 댓글에 좋아요를 눌렀는지 확인하는 메서드
    boolean existsByUserAndComment(User user, Comment comment);

    // 특정 사용자가 특정 댓글에 누른 좋아요를 삭제하는 메서드
    @Modifying
    @Transactional
    @Query("delete from CommentLike c where c.user = :user and c.comment.id = :id")
    void deleteByUserAndBoard(@Param("user") User user, @Param("id") Long id);

}
