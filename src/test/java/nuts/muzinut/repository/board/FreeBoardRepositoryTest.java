package nuts.muzinut.repository.board;

import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.FreeBoard;
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
class FreeBoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FreeBoardRepository freeBoardRepository;

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
}