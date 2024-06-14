package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class RecruitBoardRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RecruitBoardRepository recruitBoardRepository;

    @Test
    void save() {

        //given
        User user = new User();
        userRepository.save(user);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoard.createRecruitBoard(user);

        //when
        recruitBoardRepository.save(recruitBoard);

        //then
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(recruitBoard.getId());
        assertThat(findRecruitBoard.get()).isEqualTo(recruitBoard);
        assertThat(findRecruitBoard.get().getUser()).isEqualTo(user);
    }

    @Test
    void delete() {

        //given
        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        //when
        recruitBoardRepository.delete(recruitBoard);

        //then
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(recruitBoard.getId());
        assertThat(findRecruitBoard.isEmpty()).isTrue();
    }
}