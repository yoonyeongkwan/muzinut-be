package nuts.muzinut.repository.member;

import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    Optional<User> findByNickname(String nickname); //nickname 으로 한명의 회원 조회

    List<User> findAllByNickname(String nickname); //nickname 으로 검색 (아티스트 명을 검색할 때 활용)
}