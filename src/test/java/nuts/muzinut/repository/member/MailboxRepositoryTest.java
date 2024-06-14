package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.member.Mailbox;
import nuts.muzinut.domain.member.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MailboxRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailboxRepository mailboxRepository;

    @Test
    void save() {

        //given
        User user = new User("email", "password");
        userRepository.save(user);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(user);

        //when
        mailboxRepository.save(mailbox);

        //then
        Optional<Mailbox> findMailbox = mailboxRepository.findById(mailbox.getId());
        assertThat(findMailbox.get().getUser()).isEqualTo(user); //저장된 메일함의 멤버를 확인
        assertThat(findMailbox.get()).isEqualTo(mailbox); //저장된 메일함의 멤버를 확인
    }

    @Test
    void delete() {

        //given
        User user = new User("email", "password");
        userRepository.save(user);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(user);
        mailboxRepository.save(mailbox);

        //when
        mailboxRepository.delete(mailbox);

        //then
        Optional<Mailbox> findMailbox = mailboxRepository.findById(mailbox.getId());
        assertThat(findMailbox.isEmpty()).isTrue();
    }

    /**
     * 회원 삭제시 해당 회원의 우편함도 삭제한다..
     */
    @Test
    void delete_with_member() {

        //given
        User user = new User("email", "password");
        userRepository.save(user);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(user);
        mailboxRepository.save(mailbox);

        //when
        userRepository.delete(user);

        //then
        Optional<Mailbox> findMailbox = mailboxRepository.findById(mailbox.getId());
        assertThat(findMailbox.isEmpty()).isTrue(); //회원 삭제시 해당 회원의 메일함이 없다.
    }
}