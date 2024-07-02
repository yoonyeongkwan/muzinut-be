package nuts.muzinut.domain.board;

import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class BoardTest {

    @Test
    void commentSizeTest() {

        //given
        User user = new User();
        Board board = new Board();

        Comment c1 = new Comment();
        c1.addComment(user, board, "c1");
        Comment c2 = new Comment();
        c2.addComment(user, board, "c2");

        Reply r1 = new Reply();
        r1.addReply(c1, "r1", user);
        Reply r2 = new Reply();
        r2.addReply(c1, "r2", user);
        Reply r3 = new Reply();
        r3.addReply(c1, "r3", user);
        Reply r4 = new Reply();
        r4.addReply(c1, "r4", user);

        //when
        int commentSize = board.getComments().size();

        //then
        assertThat(commentSize).isEqualTo(2);
    }
}