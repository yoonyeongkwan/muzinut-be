package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.Reply;
import nuts.muzinut.domain.member.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static nuts.muzinut.domain.board.QBoard.board;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminBoardRepositoryTest {

    @Autowired AdminBoardRepository adminBoardRepository;
    @Autowired BoardRepository boardRepository;
    @Autowired ReplyRepository replyRepository;
    @Autowired CommentRepository commentRepository;
    @PersistenceContext EntityManager em;

    @Test
    void save() {

        //given
        AdminBoard adminBoard = new AdminBoard();

        //when
        adminBoardRepository.save(adminBoard);

        //then
        Optional<AdminBoard> findAdminBoard = adminBoardRepository.findById(adminBoard.getId());
        assertThat(findAdminBoard.get()).isEqualTo(adminBoard);
    }

    @Test
    @Rollback(value = false)
    void delete() {

        //given
        Board adminBoard = new AdminBoard();

        //when
        boardRepository.delete(adminBoard);

        //then
        Optional<AdminBoard> findAdminBoard = adminBoardRepository.findById(adminBoard.getId());
        assertThat(findAdminBoard.isEmpty()).isTrue();
    }

    @Test
//    @Rollback(value = false)
    void deleteById() {

        //given
        User user = new User();
        AdminBoard adminBoard = new AdminBoard();
        Comment comment = new Comment();
        comment.addComment(user, adminBoard, "c");
        Reply reply = new Reply();
        reply.addReply(comment, "c", user);
        replyRepository.save(reply);

        //when
//        boardRepository.deleteById(adminBoard.getId());
        adminBoardRepository.delete(adminBoard);
        clearContext();

//        //then
        List<AdminBoard> result = adminBoardRepository.findAll();
        List<Comment> findComments = commentRepository.findAll();
        List<Reply> findReplies = replyRepository.findAll();

        assertThat(result.size()).isEqualTo(0);
        assertThat(findComments.size()).isEqualTo(0);
        assertThat(findReplies.size()).isEqualTo(0);
    }

    @Test
    void findAllWithPaging() {

        //given
        createAdminBoards();

        //when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<AdminBoard> page = adminBoardRepository.findAll(pageRequest);

        //then
        List<AdminBoard> content = page.getContent();
        assertThat(content.size()).isEqualTo(10);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getNumber()).isEqualTo(0);
    }

    @Test
    void findBoardWithUser() {

        //given
        User user = new User();
        AdminBoard adminBoard = new AdminBoard();
        adminBoard.addBoard(user);
        adminBoardRepository.save(adminBoard);

        //when
        AdminBoard findAdminBoard = adminBoardRepository.findAdminBoardWithUser(adminBoard.getId()).get();

        //then
        assertThat(findAdminBoard).isEqualTo(adminBoard);
        assertThat(findAdminBoard.getUser()).isEqualTo(user);

    }


    private void createAdminBoards() {
        for (int i = 0; i < 30; i++) {
            AdminBoard adminBoard = new AdminBoard();
            adminBoardRepository.save(adminBoard);
        }
    }

    private void clearContext() {
        em.flush();
        em.clear();
    }
}