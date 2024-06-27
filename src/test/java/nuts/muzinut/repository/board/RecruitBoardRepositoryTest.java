package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.board.RecruitBoardGenre;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RecruitBoardRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RecruitBoardRepository recruitBoardRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        userRepository.save(user);
    }

    private RecruitBoard createRecruitBoard(String title, int view, String genre) {
        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoard.setTitle(title);
        recruitBoard.setView(view);
        if (genre != null) {
            recruitBoard.addGenre(genre);
        }
        recruitBoardRepository.save(recruitBoard);
        return recruitBoard;
    }

    private void createMultipleRecruitBoards(int count) {
        for (int i = 0; i < count; i++) {
            createRecruitBoard("Test Title " + i, i, i % 2 == 0 ? "Rock" : "Jazz");
        }
    }

    @Test
    void save() {
        //given
        RecruitBoard recruitBoard = createRecruitBoard("Test Title", 0, null);

        //when
        recruitBoardRepository.save(recruitBoard);

        //then
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(recruitBoard.getId());
        assertThat(findRecruitBoard.get()).isEqualTo(recruitBoard); // 저장된 RecruitBoard가 데이터베이스에서 올바르게 조회되는지 확인
        assertThat(findRecruitBoard.get().getUser()).isEqualTo(user); // RecruitBoard와 연관된 User가 올바르게 설정되었는지 확인
    }

    @Test
    void delete() {
        //given
        RecruitBoard recruitBoard = createRecruitBoard("Test Title", 0, null);

        //when
        recruitBoardRepository.delete(recruitBoard);

        //then
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(recruitBoard.getId());
        assertThat(findRecruitBoard.isEmpty()).isTrue(); // RecruitBoard가 데이터베이스에서 올바르게 삭제되었는지 확인
    }

    @Test
    void findByTitleContaining() {
        //given
        RecruitBoard recruitBoard1 = createRecruitBoard("Test Title 1", 0, null);
        createRecruitBoard("Another Title", 0, null);

        //when
        Page<RecruitBoard> result = recruitBoardRepository.findByTitleContaining("Test", PageRequest.of(0, 10));

        //then
        assertThat(result.getTotalElements()).isEqualTo(1); // 제목에 "Test"가 포함된 RecruitBoard가 1개인지 확인
        assertThat(result.getContent().get(0)).isEqualTo(recruitBoard1); // 조회된 RecruitBoard가 예상한 것과 일치하는지 확인
    }

    @Test
    void findAllByOrderByCreatedDateDesc() {
        //given
        createMultipleRecruitBoards(25);

        //when
        Page<RecruitBoard> result = recruitBoardRepository.findAllByOrderByCreatedDateDesc(PageRequest.of(0, 10));

        //then
        assertThat(result.getTotalElements()).isEqualTo(25); // 총 25개의 RecruitBoard가 있는지 확인
        assertThat(result.getContent().size()).isEqualTo(10); // 페이지 크기가 10인지 확인
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Test Title 24"); // 최신 순으로 정렬된 첫 번째 RecruitBoard의 제목이 "Test Title 24"인지 확인
    }

    @Test
    void findAllByOrderByViewDesc() {
        //given
        createMultipleRecruitBoards(25);

        //when
        Page<RecruitBoard> result = recruitBoardRepository.findAllByOrderByViewDesc(PageRequest.of(0, 10));

        //then
        assertThat(result.getTotalElements()).isEqualTo(25); // 총 25개의 RecruitBoard가 있는지 확인
        assertThat(result.getContent().size()).isEqualTo(10); // 페이지 크기가 10인지 확인
        assertThat(result.getContent().get(0).getView()).isEqualTo(24); // 조회수 순으로 정렬된 첫 번째 RecruitBoard의 조회수가 24인지 확인
    }

    @Test
    void findAllByGenre() {
        //given
        createMultipleRecruitBoards(25);

        //when
        Page<RecruitBoard> result = recruitBoardRepository.findAllByGenre("Rock", PageRequest.of(0, 10));

        //then
        assertThat(result.getTotalElements()).isEqualTo(13); // "Rock" 장르의 RecruitBoard가 총 13개인지 확인
        assertThat(result.getContent().size()).isEqualTo(10); // 페이지 크기가 10인지 확인
        assertThat(result.getContent().get(0).getRecruitBoardGenres().stream()
                .map(RecruitBoardGenre::getGenre)
                .collect(Collectors.toList())).contains("Rock"); // 조회된 첫 번째 RecruitBoard의 장르 목록에 "Rock"이 포함되어 있는지 확인
    }


}
