package nuts.muzinut.repository.board.query;

import com.querydsl.core.Tuple;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.board.CommentRepository;
import nuts.muzinut.repository.board.FreeBoardRepository;
import nuts.muzinut.repository.board.LikeRepository;
import nuts.muzinut.repository.board.ReplyRepository;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class FreeBoardQueryRepositoryTest {

    @Autowired FreeBoardQueryRepository queryRepository;
    @Autowired FreeBoardRepository freeBoardRepository;
    @Autowired UserRepository userRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired LikeRepository likeRepository;
    @Autowired ReplyRepository replyRepository;

    @Rollback(value = false)
    @Test
    void queryTest1() {

        //given
        User user = new User("u", "p", "nananana");
        Board freeBoard = new FreeBoard();
        freeBoard.addBoard(user);
        Comment comment = new Comment();
        comment.addComment(user, freeBoard, "c1");
        Comment comment2 = new Comment();
        comment2.addComment(user, freeBoard, "c2");
        Reply reply = new Reply();
        reply.addReply(comment, "r1", user);
        replyRepository.save(reply);

        //when
        List<Tuple> result = queryRepository.getDetailFreeBoardTest(freeBoard.getId());

        //then
        for (Tuple t : result) {
            log.info("tuple: {}", t);
        }

        Tuple first = result.getFirst();
        Board findBoard = first.get(QBoard.board);
        for (Comment c : findBoard.getComments()) {
            log.info("c: {}", c.getContent());
            for (Reply r: c.getReplies()) {
                log.info("r: {}", r.getContent());
            }
        }
        FreeBoard findFreeBoard = first.get(QFreeBoard.freeBoard);
        log.info("findFreeBoard writer: {}", findFreeBoard.getUser().getNickname());
    }

    @Rollback(value = false)
    @Test
    void queryTest2() {

        //given
        User user = new User("u", "p", "nananana");
        FreeBoard freeBoard = new FreeBoard();
        freeBoard.addBoard(user);
        freeBoardRepository.save(freeBoard);

        //when
        List<Tuple> result = queryRepository.getDetailFreeBoardTest(freeBoard.getId());

        //then
        for (Tuple t : result) {
            log.info("tuple: {}", t);
        }

        Tuple first = result.getFirst();
        FreeBoard findFreeBoard = first.get(QFreeBoard.freeBoard);
        log.info("findFreeBoard writer: {}", findFreeBoard.getUser().getNickname());
    }
}