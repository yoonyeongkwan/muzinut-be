package nuts.muzinut.repository.member;

import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.member.Block;
import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {

    void deleteByUserAndBlockUser(User user ,User blockUser);

    Optional<Block> findByBlockUser(User blockUser);

    @Query("select b from Block b join fetch b.user where b.user = :user")
    List<Block> findBlocksByUser(@Param("user") User user);
}
