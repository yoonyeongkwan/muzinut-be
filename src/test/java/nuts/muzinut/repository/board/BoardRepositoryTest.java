package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.Reply;
import nuts.muzinut.domain.member.User;
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

    @Autowired BoardRepository boardRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired ReplyRepository replyRepository;

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
}