package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.CommentLike;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.member.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentLikeRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired BoardRepository boardRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired CommentLikeRepository likeRepository;

    @Test
    void existsByUserAndCommentFalse() {

        //given
        User user = new User();
        userRepository.save(user);
        Board board = new Board();
        boardRepository.save(board);
        Comment comment = new Comment();
        comment.addComment(user, board, "c1");
        commentRepository.save(comment);

        //when
        boolean isLike = likeRepository.existsByUserAndComment(user, comment);

        //then
        assertThat(isLike).isFalse();
    }

    @Test
    void existsByUserAndCommentTrue() {

        //given
        User user = new User();
        userRepository.save(user);
        Board board = new Board();
        boardRepository.save(board);
        Comment comment = new Comment();
        comment.addComment(user, board, "c1");
        commentRepository.save(comment);
        CommentLike commentLike = new CommentLike();
        commentLike.addLike(user, comment);
        likeRepository.save(commentLike);

        //when
        boolean isLike = likeRepository.existsByUserAndComment(user, comment);

        //then
        assertThat(isLike).isTrue();
    }

    @Test
    void deleteByUserAndBoard() {
        User user = new User();
        userRepository.save(user);
        Board board = new Board();
        boardRepository.save(board);
        Comment comment = new Comment();
        comment.addComment(user, board, "c1");
        commentRepository.save(comment);
        CommentLike commentLike = new CommentLike();
        commentLike.addLike(user, comment);
        likeRepository.save(commentLike);

        likeRepository.existsByUserAndComment(user, comment); //좋아요 누르기

        //when
        likeRepository.deleteByUserAndBoard(user, comment.getId()); //좋아요 취소

        //then
        boolean isLike = likeRepository.existsByUserAndComment(user, comment);
        assertThat(isLike).isFalse();
    }
}