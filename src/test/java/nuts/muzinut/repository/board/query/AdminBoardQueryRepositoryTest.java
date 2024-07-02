package nuts.muzinut.repository.board.query;

import com.querydsl.core.Tuple;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.QUser;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.board.*;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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
    @Autowired BookmarkRepository bookmarkRepository;

    @Rollback(value = false)
    @Test
    void queryTest() {

        //given
        User user = new User("username", "pass", "nick");
        User commentUser = new User("u2", "pass2", "nick2");
        userRepository.save(user);
        userRepository.save(commentUser);
        AdminBoard board = new AdminBoard();
        board.addBoard(user);
        adminBoardRepository.save(board);
        Comment comment = new Comment();
        comment.addComment(commentUser, board, "c1");
        Comment comment2 = new Comment();
        comment2.addComment(commentUser, board, "c1");
        Reply reply = new Reply();
        reply.addReply(comment, "r1", commentUser);
        replyRepository.save(reply);

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(user, board);
        bookmarkRepository.save(bookmark);

        Like like = new Like();
        like.addLike(user, board);
        likeRepository.save(like);

        //when
        List<Tuple> result = queryRepository.getDetailAdminBoard(board.getId(), user);

        //then
        for (Tuple t : result) {
            log.info("tuple: {}", t);
        }

/*        Tuple first = result.getFirst();
        Board findBoard = first.get(QBoard.board);
        log.info("adminBoard writer: {}", findBoard.getUser().getNickname());

        for (Comment c : findBoard.getComments()) {
            log.info("comments: {}", c.getContent());

            for (Reply r : c.getReplies()) {
                log.info("replies: {}", r.getContent());
            }
        }*/
    }
}