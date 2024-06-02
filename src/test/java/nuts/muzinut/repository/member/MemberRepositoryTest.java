package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.member.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
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
        member.setRole(Role.ROLE_USER); //enum type test

        //when
        memberRepository.save(member);

        //then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember.get().getEmail()).isEqualTo("email");
        assertThat(findMember.get().getPassword()).isEqualTo("password");
        assertThat(findMember.get().getRole().toString().equals("ROLE_USER")).isTrue();
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