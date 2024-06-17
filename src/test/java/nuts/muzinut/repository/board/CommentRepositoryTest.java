package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.MusicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired FreeBoardRepository freeBoardRepository;
    @Autowired LoungeRepository loungeRepository;
    @Autowired RecruitBoardRepository recruitBoardRepository;
    @Autowired MusicRepository musicRepository;
    @Autowired CommentRepository commentRepository;

    @Test
    void musicComment() {

        //given
        User user = new User();
        userRepository.save(user);

        Song song = new Song();
        musicRepository.save(song);

        Comment comment = new Comment();
        comment.addComment(user, song.getId(), BoardType.MUSIC, "content");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> findComment = commentRepository.findById(comment.getId());
        assertThat(findComment.get()).isEqualTo(comment);
        assertThat(findComment.get().getUser()).isEqualTo(user);
        assertThat(findComment.get().getBoardId()).isEqualTo(song.getId());
    }

    @Test
    void freeBoardComment() {

        //given
        User user = new User();
        userRepository.save(user);

        FreeBoard freeBoard = new FreeBoard();
        freeBoardRepository.save(freeBoard);

        Comment comment = new Comment();
        comment.addComment(user, freeBoard.getId(), BoardType.FREE, "content");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> findComment = commentRepository.findById(comment.getId());
        assertThat(findComment.get()).isEqualTo(comment);
        assertThat(findComment.get().getUser()).isEqualTo(user);
        assertThat(findComment.get().getBoardId()).isEqualTo(freeBoard.getId());
    }

    @Test
    void LoungeComment() {

        //given
        User user = new User();
        userRepository.save(user);

        Lounge lounge = new Lounge();
        loungeRepository.save(lounge);

        Comment comment = new Comment();
        comment.addComment(user, lounge.getId(), BoardType.LOUNGE, "content");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> findComment = commentRepository.findById(comment.getId());
        assertThat(findComment.get()).isEqualTo(comment);
        assertThat(findComment.get().getUser()).isEqualTo(user);
        assertThat(findComment.get().getBoardId()).isEqualTo(lounge.getId());
    }

    @Test
    void RecruitBoardComment() {

        //given
        User user = new User();
        userRepository.save(user);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        Comment comment = new Comment();
        comment.addComment(user, recruitBoard.getId(), BoardType.LOUNGE, "content");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> findComment = commentRepository.findById(comment.getId());
        assertThat(findComment.get()).isEqualTo(comment);
        assertThat(findComment.get().getUser()).isEqualTo(user);
        assertThat(findComment.get().getBoardId()).isEqualTo(recruitBoard.getId());
    }

    @Test
    void delete() {

        //given
        Comment comment = new Comment();
        commentRepository.save(comment);

        //when
        commentRepository.delete(comment);

        //then
        Optional<Comment> findComment = commentRepository.findById(comment.getId());
        assertThat(findComment.isEmpty()).isTrue();
    }

    //게시판이 삭제 될때 해당 게시판의 댓글이 삭제되는 로직은 cascade 로 구현하지 않았기에 따로 쿼리를 작성해야함.
}