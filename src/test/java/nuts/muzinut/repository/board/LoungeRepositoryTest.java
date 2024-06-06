package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Lounge;
import nuts.muzinut.domain.member.Member;
import nuts.muzinut.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class LoungeRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LoungeRepository loungeRepository;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Lounge lounge = new Lounge();
        lounge.createLounge(member);

        //when
        loungeRepository.save(lounge);

        //then
        Optional<Lounge> findLounge = loungeRepository.findById(lounge.getId());
        assertThat(findLounge.get()).isEqualTo(lounge);
        assertThat(findLounge.get().getMember()).isEqualTo(member);
    }

    @Test
    void delete() {

        //given
        Lounge lounge = new Lounge();
        loungeRepository.save(lounge);

        //when
        loungeRepository.delete(lounge);

        //then
        Optional<Lounge> findLounge = loungeRepository.findById(lounge.getId());
        assertThat(findLounge.isEmpty()).isTrue();
    }
}