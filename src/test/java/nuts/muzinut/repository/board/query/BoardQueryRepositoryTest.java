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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class BoardQueryRepositoryTest {

    @Autowired
    BoardQueryRepository repository;
    @Autowired AdminBoardRepository adminBoardRepository;
    @Autowired UserRepository userRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired LikeRepository likeRepository;
    @Autowired ReplyRepository replyRepository;


    @Test
    void detailAdminBoards() {

        //given
        User user = createUser();
        AdminBoard board = createBoard();
//        Comment comment = createComment(user, board);
//        Comment comment2 = createComment(user, board);
//        Reply reply1 = createReply(user, comment);
//        Reply reply2 = createReply(user, comment);
//        Like like = createLike(user, board);

        //when
/*        List<Board> result = repository.getDetailBoardTest(board.getId());
        Board findBoard = result.getFirst();
        assertThat(findBoard).isEqualTo(board);

        List<Comment> comments = findBoard.getComments();
        for (Comment c : comments) {
            c.getUser().getNickname();

            List<Reply> replies = c.getReplies();
            for (Reply reply : replies) {
                reply.getUser();
            }
        }*/

        List<Tuple> result = repository.getDetailBoardTest(board.getId());

        for (Tuple t : result) {
            log.info("tuple: {}", t);
        }
    }

    private AdminBoard createBoard() {
        AdminBoard adminBoard = new AdminBoard();
        return adminBoardRepository.save(adminBoard);
    }

    private Comment createComment(User user, Board board) {
        Comment comment = new Comment();
        comment.addComment(user, board, "co!");
        return commentRepository.save(comment);
    }

    private User createUser() {
        User user = new User("user", "1111");
        return userRepository.save(user);
    }

    private Like createLike(User user, Board board) {
        Like like = new Like();
        like.addLike(user, board);
        return likeRepository.save(like);
    }

    private Reply createReply(User user, Comment comment) {
        Reply reply = new Reply();
        reply.addReply(comment, "reply!", user);
        return replyRepository.save(reply);
    }
}