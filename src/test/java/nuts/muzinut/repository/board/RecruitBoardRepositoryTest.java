package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.Member;
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
class RecruitBoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RecruitBoardRepository recruitBoardRepository;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoard.createRecruitBoard(member);

        //when
        recruitBoardRepository.save(recruitBoard);

        //then
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(recruitBoard.getId());
        assertThat(findRecruitBoard.get()).isEqualTo(recruitBoard);
        assertThat(findRecruitBoard.get().getMember()).isEqualTo(member);
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