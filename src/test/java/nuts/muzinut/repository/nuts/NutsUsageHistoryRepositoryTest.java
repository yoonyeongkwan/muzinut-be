package nuts.muzinut.repository.nuts;

import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.nuts.NutsUsageHistory;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class NutsUsageHistoryRepositoryTest {

    @Autowired NutsUsageHistoryRepository nutsUsageHistoryRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void save() {

        //given
        User user = new User();
        userRepository.save(user);

        NutsUsageHistory nutsUsageHistory = new NutsUsageHistory();
        nutsUsageHistory.createNutsHistory(user);

        //when
        nutsUsageHistoryRepository.save(nutsUsageHistory);

        //then
        Optional<NutsUsageHistory> result = nutsUsageHistoryRepository.findById(nutsUsageHistory.getId());
        assertThat(result.get()).isEqualTo(nutsUsageHistory);
        assertThat(result.get().getUser()).isEqualTo(user);
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
        User user = new User();
        userRepository.save(user);

        NutsUsageHistory nutsUsageHistory = new NutsUsageHistory();
        nutsUsageHistory.createNutsHistory(user);
        nutsUsageHistoryRepository.save(nutsUsageHistory);

        //when
        userRepository.delete(user);

        //then
        Optional<NutsUsageHistory> result = nutsUsageHistoryRepository.findById(nutsUsageHistory.getId());
        assertThat(result.isEmpty()).isTrue();
    }
}