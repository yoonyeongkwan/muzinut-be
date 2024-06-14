package nuts.muzinut.repository.member;

import nuts.muzinut.domain.member.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
