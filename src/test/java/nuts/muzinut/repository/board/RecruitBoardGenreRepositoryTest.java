package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.board.RecruitBoardGenre;
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
class RecruitBoardGenreRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RecruitBoardRepository recruitBoardRepository;
    @Autowired
    RecruitBoardGenreRepository recruitBoardGenreRepository;

    @Test
    void save() {

        //given
        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        RecruitBoardGenre recruitBoardGenre = new RecruitBoardGenre();
        recruitBoardGenre.addRecruitGenre(recruitBoard);

        //when
        recruitBoardGenreRepository.save(recruitBoardGenre);

        //then
        Optional<RecruitBoardGenre> result = recruitBoardGenreRepository.findById(recruitBoardGenre.getId());
        assertThat(result.get()).isEqualTo(recruitBoardGenre);
        assertThat(result.get().getRecruitBoard()).isEqualTo(recruitBoard);
    }

    @Test
    void delete() {

        //given
        RecruitBoardGenre recruitBoardGenre = new RecruitBoardGenre();
        recruitBoardGenreRepository.save(recruitBoardGenre);

        //when
        recruitBoardGenreRepository.delete(recruitBoardGenre);

        //then
        Optional<RecruitBoardGenre> result = recruitBoardGenreRepository.findById(recruitBoardGenre.getId());
        assertThat(result.isEmpty()).isTrue();
    }

    //모집 게시판이 삭제되면 해당 게시판의 장르들도 삭제되어야 한다
    @Test
    void deleteRecruitBoard() {

        //given
        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        RecruitBoardGenre recruitBoardGenre = new RecruitBoardGenre();
        recruitBoardGenre.addRecruitGenre(recruitBoard);
        recruitBoardGenreRepository.save(recruitBoardGenre);

        //when
        recruitBoardRepository.delete(recruitBoard);

        //then
        Optional<RecruitBoardGenre> result = recruitBoardGenreRepository.findById(recruitBoardGenre.getId());
        assertThat(result.isEmpty()).isTrue();
    }
}