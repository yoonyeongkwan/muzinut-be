package nuts.muzinut.repository.nuts;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.nuts.NutsUsageHistory;
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
class NutsUsageHistoryRepositoryTest {

    @Autowired NutsUsageHistoryRepository nutsUsageHistoryRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        NutsUsageHistory nutsUsageHistory = new NutsUsageHistory();
        nutsUsageHistory.createNutsHistory(member);

        //when
        nutsUsageHistoryRepository.save(nutsUsageHistory);

        //then
        Optional<NutsUsageHistory> result = nutsUsageHistoryRepository.findById(nutsUsageHistory.getId());
        assertThat(result.get()).isEqualTo(nutsUsageHistory);
        assertThat(result.get().getMember()).isEqualTo(member);
    }

    @Test
    void delete() {

        //given
        NutsUsageHistory nutsUsageHistory = new NutsUsageHistory();
        nutsUsageHistoryRepository.save(nutsUsageHistory);

        //when
        nutsUsageHistoryRepository.delete(nutsUsageHistory);

        //then
        Optional<NutsUsageHistory> result = nutsUsageHistoryRepository.findById(nutsUsageHistory.getId());
        assertThat(result.isEmpty()).isTrue();
    }

    //회원이 삭제되면 해당 회원의 너츠 사용 내역도 삭제된다. (너무 중요한 데이터라 논의 필요하다고 생각함)
    @Test
    void deleteMember() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        NutsUsageHistory nutsUsageHistory = new NutsUsageHistory();
        nutsUsageHistory.createNutsHistory(member);
        nutsUsageHistoryRepository.save(nutsUsageHistory);

        //when
        memberRepository.delete(member);

        //then
        Optional<NutsUsageHistory> result = nutsUsageHistoryRepository.findById(nutsUsageHistory.getId());
        assertThat(result.isEmpty()).isTrue();
    }
}