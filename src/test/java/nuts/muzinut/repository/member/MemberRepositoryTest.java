package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {

        //given
        Member member = new Member("email", "password");

        //when
        memberRepository.save(member);

        //then
        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst().getEmail()).isEqualTo("email");
        assertThat(result.getFirst().getPassword()).isEqualTo("password");
    }

    @Test
    void remove() {

        //given
        Member member = new Member("email", "password");

        //when
        memberRepository.save(member);

        //then
        memberRepository.delete(member);
        em.flush();
        em.clear();

        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }
}