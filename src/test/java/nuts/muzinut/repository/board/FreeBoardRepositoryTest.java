package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.member.Member;
import nuts.muzinut.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FreeBoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FreeBoardRepository freeBoardRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        FreeBoard freeBoard = new FreeBoard();
        freeBoard.createFreeBoard(member);

        //when
        freeBoardRepository.save(freeBoard);

        //then
        Optional<FreeBoard> findFreeBoard = freeBoardRepository.findById(freeBoard.getId());
        assertThat(findFreeBoard.get()).isEqualTo(freeBoard);
        assertThat(findFreeBoard.get().getMember()).isEqualTo(member);
    }

    @Test
    void delete() {

        //given
        FreeBoard freeBoard = new FreeBoard();
        freeBoardRepository.save(freeBoard);

        //when
        freeBoardRepository.delete(freeBoard);

        //then
        Optional<FreeBoard> findFreeBoard = freeBoardRepository.findById(freeBoard.getId());
        assertThat(findFreeBoard.isEmpty()).isTrue();
    }

    @Test
    void mainPaging() {

        //given
        createFreeBoards();

        //when
        //첫번째 파라미터는 시작할 페이지, 두번쨰 파라미터는 페이지당 데이터 개수, 세번째 파라미터는 정렬 기준
        PageRequest pageRequest = PageRequest.of(2, 3, Sort.by(Sort.Direction.DESC, "createdDt"));
        Page<FreeBoard> page = freeBoardRepository.findAll(pageRequest);

        //then
        List<FreeBoard> content = page.getContent();
        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getNumber()).isEqualTo(2); //현재 페이지 번호
        assertThat(page.getTotalPages()).isEqualTo(4); //전체 페이지 수
        assertThat(page.isFirst()).isFalse(); //첫번째 페이지가 아님
        assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가
        assertThat(page.getTotalElements()).isEqualTo(10); //전체 데이터 수

    }

    private void createFreeBoards() {
        for (int i = 0; i < 10; i++) {
            FreeBoard freeBoard = new FreeBoard();
            freeBoardRepository.save(freeBoard);
        }
    }
}