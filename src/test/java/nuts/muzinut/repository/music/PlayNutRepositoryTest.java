package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.PlayNut;
import nuts.muzinut.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PlayNutRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PleNutRepository pleNutRepository;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        PlayNut playNut = new PlayNut();
        playNut.createPleNut(member);

        //when
        pleNutRepository.save(playNut);

        //then
        Optional<PlayNut> findPleNut = pleNutRepository.findById(playNut.getId());
        assertThat(findPleNut.get()).isEqualTo(playNut);
        assertThat(findPleNut.get().getMember()).isEqualTo(member);
    }

    @Test
    void delete() {

        //given
        PlayNut playNut = new PlayNut();
        pleNutRepository.save(playNut);

        //when
        pleNutRepository.delete(playNut);

        //then
        Optional<PlayNut> findPleNut = pleNutRepository.findById(playNut.getId());
        assertThat(findPleNut.isEmpty()).isTrue();
    }

    //회원이 삭제되면 해당 회원의 플리넛도 삭제되어야 한다.
    @Test
    void deleteMember() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        PlayNut playNut = new PlayNut();
        playNut.createPleNut(member);
        pleNutRepository.save(playNut);

        //when
        memberRepository.delete(member);

        //then
        Optional<PlayNut> findPleNut = pleNutRepository.findById(playNut.getId());
        assertThat(findPleNut.isEmpty()).isTrue();
    }
}