package nuts.muzinut.repository.board;

import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.MusicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired FreeBoardRepository freeBoardRepository;
    @Autowired AdminBoardRepository adminBoardRepository;
    @Autowired LoungeRepository loungeRepository;
    @Autowired RecruitBoardRepository recruitBoardRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired ReplyRepository replyRepository;

    @Test
    void save() {

        //given
        Comment comment = new Comment();

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> result = commentRepository.findById(comment.getId());
        assertThat(result.get()).isEqualTo(comment);
    }

    @Test
    void freeBoardComment() {

        //given
        User user = new User();
        FreeBoard freeBoard = new FreeBoard();
        Comment comment = new Comment();
        comment.addComment(user, freeBoard, "h");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> result = commentRepository.findById(comment.getId());
        assertThat(result.get().getBoard()).isEqualTo(freeBoard);
        assertThat(result.get().getUser()).isEqualTo(user);
    }

    @Test
    void adminBoardComment() {

        //given
        User user = new User();
        AdminBoard adminBoard = new AdminBoard();
        Comment comment = new Comment();
        comment.addComment(user, adminBoard, "h");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> result = commentRepository.findById(comment.getId());
        assertThat(result.get().getBoard()).isEqualTo(adminBoard);
        assertThat(result.get().getUser()).isEqualTo(user);

    }

    @Test
    void loungeComment() {

        //given
        User user = new User();
        Lounge lounge = new Lounge();
        Comment comment = new Comment();
        comment.addComment(user, lounge, "h");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> result = commentRepository.findById(comment.getId());
        assertThat(result.get().getBoard()).isEqualTo(lounge);
        assertThat(result.get().getUser()).isEqualTo(user);

    }

    @Test
    void recruitComment() {

        //given
        User user = new User();
        RecruitBoard recruitBoard = new RecruitBoard();
        Comment comment = new Comment();
        comment.addComment(user, recruitBoard, "h");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> result = commentRepository.findById(comment.getId());
        assertThat(result.get().getBoard()).isEqualTo(recruitBoard);
        assertThat(result.get().getUser()).isEqualTo(user);

    }

    @Test
    void boardComments() {

        //given
        User user = new User();
        AdminBoard adminBoard = new AdminBoard();
        Comment comment1 = createComment(user, adminBoard);
        Comment comment2 = createComment(user, adminBoard);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        //when
        List<Comment> findComments = commentRepository.findBoardComments(adminBoard);

        //then
        assertThat(findComments.size()).isEqualTo(2);
    }

    private Comment createComment(User user, Board board) {
        Comment comment = new Comment();
        comment.addComment(user, board, "h");
        return comment;
    }
}