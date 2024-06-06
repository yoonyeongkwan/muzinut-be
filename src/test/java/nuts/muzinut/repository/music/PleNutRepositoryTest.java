package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.PleNut;
import nuts.muzinut.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PleNutRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PleNutRepository pleNutRepository;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        PleNut pleNut = new PleNut();
        pleNut.createPleNut(member);

        //when
        pleNutRepository.save(pleNut);

        //then
        Optional<PleNut> findPleNut = pleNutRepository.findById(pleNut.getId());
        assertThat(findPleNut.get()).isEqualTo(pleNut);
        assertThat(findPleNut.get().getMember()).isEqualTo(member);
    }

    @Test
    void delete() {

        //given
        PleNut pleNut = new PleNut();
        pleNutRepository.save(pleNut);

        //when
        pleNutRepository.delete(pleNut);

        //then
        Optional<PleNut> findPleNut = pleNutRepository.findById(pleNut.getId());
        assertThat(findPleNut.isEmpty()).isTrue();
    }

    //회원이 삭제되면 해당 회원의 플리넛도 삭제되어야 한다.
    @Test
    void deleteMember() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        PleNut pleNut = new PleNut();
        pleNut.createPleNut(member);
        pleNutRepository.save(pleNut);

        //when
        memberRepository.delete(member);

        //then
        Optional<PleNut> findPleNut = pleNutRepository.findById(pleNut.getId());
        assertThat(findPleNut.isEmpty()).isTrue();
    }
}