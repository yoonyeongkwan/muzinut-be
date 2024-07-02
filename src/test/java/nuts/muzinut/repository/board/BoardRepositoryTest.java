package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.Reply;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.member.UserRepository;
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
class BoardRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired BoardRepository boardRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired ReplyRepository replyRepository;
    @PersistenceContext EntityManager em;

    @Test
    void deleteWithComment() {

        //given
        Board board = new Board();
        User user = new User();
        Comment comment = new Comment();
        comment.addComment(user, board, "c");
        commentRepository.save(comment);

        //when
        boardRepository.deleteById(board.getId());

        //then
        Optional<Board> findBoard = boardRepository.findById(board.getId());
        assertThat(findBoard.isEmpty()).isTrue();
    }

    @Test
    void deleteWithCommentReply() {

        //given
        Board board = new Board();
        User user = new User();
        Comment comment = new Comment();
        comment.addComment(user, board, "c");
        Reply reply = new Reply();
        reply.addReply(comment, "c", user);
        replyRepository.save(reply);

        //when
        boardRepository.deleteById(board.getId());

        //then
        Optional<Board> findBoard = boardRepository.findById(board.getId());
        assertThat(findBoard.isEmpty()).isTrue();
    }

    @Test
    void commentSize() {

        //given
        User user = new User();
        userRepository.save(user);
        Board board = new Board();
        boardRepository.save(board);

        Comment c1 = new Comment();
        c1.addComment(user, board, "c1");
        commentRepository.save(c1);
        Comment c2 = new Comment();
        c2.addComment(user, board, "c2");
        commentRepository.save(c2);

        Reply r1 = new Reply();
        r1.addReply(c1, "r1", user);
        replyRepository.save(r1);
        Reply r2 = new Reply();
        r2.addReply(c1, "r2", user);
        replyRepository.save(r2);
        Reply r3 = new Reply();
        r3.addReply(c1, "r3", user);
        replyRepository.save(r3);
        Reply r4 = new Reply();
        r4.addReply(c1, "r4", user);
        replyRepository.save(r4);

        //when
        em.flush();
        em.clear();

        //then
        Board findBoard = boardRepository.findById(board.getId()).get();
        assertThat(findBoard.getComments().size()).isEqualTo(2);
    }
}