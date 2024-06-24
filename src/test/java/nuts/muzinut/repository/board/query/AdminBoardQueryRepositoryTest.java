package nuts.muzinut.repository.board.query;

import com.querydsl.core.Tuple;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.board.CommentRepository;
import nuts.muzinut.repository.board.LikeRepository;
import nuts.muzinut.repository.board.ReplyRepository;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static nuts.muzinut.domain.board.QBoard.*;
import static nuts.muzinut.domain.board.QBoard.board;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class AdminBoardQueryRepositoryTest {

    @Autowired BoardQueryRepository repository;
    @Autowired AdminBoardRepository adminBoardRepository;
    @Autowired UserRepository userRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired LikeRepository likeRepository;
    @Autowired ReplyRepository replyRepository;
    @Autowired AdminBoardQueryRepository queryRepository;

    @Test
    void queryTest() {

        //given
        User user = new User("username", "pass", "nick");
        User commentUser = new User("u2", "pass2", "nick2");
        Board board = new Board();
        board.addBoard(user);
        Comment comment = new Comment();
        comment.addComment(commentUser, board, "c1");
        Comment comment2 = new Comment();
        comment2.addComment(commentUser, board, "c1");
        Reply reply = new Reply();
        reply.addReply(comment, "r1", commentUser);
        replyRepository.save(reply);

        //when
        List<Tuple> result = queryRepository.getDetailBoard(board.getId());

        //then
        for (Tuple t : result) {
            log.info("tuple: {}", t);
        }

        Tuple first = result.getFirst();
        Board findBoard = first.get(QBoard.board);
        log.info("adminBoard writer: {}", findBoard.getUser().getNickname());
    }
}