package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Like;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 특정 게시글의 좋아요 수를 반환하는 메서드
    Long countByBoard(Board board);

    // 특정 사용자가 특정 게시글에 좋아요를 눌렀는지 확인하는 메서드
    boolean existsByUserAndBoard(User user, Board board);

    // 특정 사용자가 특정 게시글에 누른 좋아요를 삭제하는 메서드
    @Modifying
    @Transactional
    @Query("delete from Like l where l.user = :user and l.board = :board")
    void deleteByUserAndBoard(@Param("user") User user, @Param("board") Board board);
}
