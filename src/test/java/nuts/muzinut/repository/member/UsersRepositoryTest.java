package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.member.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class UsersRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired UserRepository userRepository;
    @Autowired FollowRepository followRepository;
    @Autowired MailboxRepository mailboxRepository;

    @Rollback(value = false)
    @Test
    void save() {

        //given
        User user = new User("email", "password");
        user.setRole(Role.USER); //enum type test

        //when
        userRepository.save(user);

        //then
        Optional<User> findMember = userRepository.findById(user.getId());
        assertThat(findMember.get().getUsername()).isEqualTo("email");
        assertThat(findMember.get().getPassword()).isEqualTo("password");
        assertThat(findMember.get().getRole().toString().equals("USER")).isTrue();
    }

    @Test
    void remove() {

        //given
        User user = new User("email", "password");

        //when
        userRepository.save(user);

        //then
        userRepository.delete(user);
        em.flush();
        em.clear();

        List<User> result = userRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }
}