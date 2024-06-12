package nuts.muzinut.repository.member;

import nuts.muzinut.domain.member.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
