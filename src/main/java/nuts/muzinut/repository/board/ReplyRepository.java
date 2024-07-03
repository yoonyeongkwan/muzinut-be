package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Reply;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // 특정 댓글에 달린 모든 대댓글을 찾는 메서드
    @Query("select r from Reply r join fetch r.user u join fetch r.comment c where r.comment = :comment")
    List<Reply> findByComment(@Param("comment") Comment comment);

    // 특정 사용자가 작성한 모든 대댓글을 찾는 메서드
    @Query("select r from Reply r join fetch r.user where r.user = :user")
    List<Reply> findByUser(@Param("user") User user);

    // 특정 댓글에 달린 대댓글을 삭제하는 메서드
    @Modifying
    @Query("delete from Reply r where r.comment = :comment")
    void deleteByComment(@Param("comment") Comment comment);
}
