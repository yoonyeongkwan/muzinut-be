package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.member.Mailbox;
import nuts.muzinut.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MailboxRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MailboxRepository mailboxRepository;

    @Test
    void save() {

        //given
        Member member = new Member("email", "password");
        memberRepository.save(member);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(member);

        //when
        mailboxRepository.save(mailbox);

        //then
        Optional<Mailbox> findMailbox = mailboxRepository.findById(mailbox.getId());
        assertThat(findMailbox.get().getMember()).isEqualTo(member); //저장된 메일함의 멤버를 확인
        assertThat(findMailbox.get()).isEqualTo(mailbox); //저장된 메일함의 멤버를 확인
    }

    @Test
    void delete() {

        //given
        Member member = new Member("email", "password");
        memberRepository.save(member);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(member);
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
        Member member = new Member("email", "password");
        memberRepository.save(member);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(member);
        mailboxRepository.save(mailbox);

        //when
        memberRepository.delete(member);

        //then
        Optional<Mailbox> findMailbox = mailboxRepository.findById(mailbox.getId());
        assertThat(findMailbox.isEmpty()).isTrue(); //회원 삭제시 해당 회원의 메일함이 없다.
    }
}