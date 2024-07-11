package nuts.muzinut;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.dto.security.AuthorityDto;
import nuts.muzinut.repository.board.CommentRepository;
import nuts.muzinut.repository.board.LikeRepository;
import nuts.muzinut.repository.board.ReplyRepository;
import nuts.muzinut.repository.board.*;
import nuts.muzinut.service.DataInitService;
import nuts.muzinut.repository.board.query.BoardQueryRepository;
import nuts.muzinut.repository.member.AuthorityRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.board.AdminBoardService;
import nuts.muzinut.service.member.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final AdminBoardRepository adminBoardRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final ReplyRepository replyRepository;
    private final DataInitService dataInitService;
    private final FreeBoardRepository freeBoardRepository;
    private final UserService userService;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        dataInitService.initializeData();
    }

//    @PostConstruct
    public void adminBoardScenario() {
        User user1 = new User();
        user1.setNickname("tom");
        User user2 = new User();
        user2.setNickname("nick");
//        userRepository.save(user1);
//        userRepository.save(user2);

        Board adminBoard = new AdminBoard();

        Comment comment1 = new Comment();
        comment1.addComment(user1, adminBoard, "sample");
        Reply reply1 = new Reply();
        reply1.addReply(comment1, "댓글1", user1);
//        replyRepository.save(reply1);

        Comment comment2 = new Comment();
        comment2.addComment(user1, adminBoard, "sample");
        Reply reply2 = new Reply();
        reply2.addReply(comment1, "댓글2", user1);
        replyRepository.save(reply2);

        createAdminBoards();
    }

//    @PostConstruct
    public void FreeBoardScenario() {
        User user1 = new User();
        user1.setNickname("tom");
        User user2 = new User();
        user2.setNickname("nick");

        Board freeBoard = new FreeBoard();
        freeBoard.addBoard(user1);
//        createFreeBoards();

        Comment comment1 = new Comment();
        comment1.addComment(user1, freeBoard, "sample");
        Reply reply1 = new Reply();
        reply1.addReply(comment1, "댓글1", user1);
//        replyRepository.save(reply1);

        Comment comment2 = new Comment();
        comment2.addComment(user1, freeBoard, "sample");
        Reply reply2 = new Reply();
        reply2.addReply(comment1, "댓글2", user1);
        replyRepository.save(reply2);
    }

//    @PostConstruct
    public void commentLike() {
        Comment comment = new Comment();
        commentRepository.save(comment);
    }

    private void createAdminBoards() {
        for (int i = 0; i < 20; i++) {
            AdminBoard adminBoard = new AdminBoard();
            adminBoardRepository.save(adminBoard);
        }
    }

    private void createFreeBoards() {
        for (int i = 0; i < 20; i++) {
            User user1 = new User();
            user1.setNickname("free-test");
            FreeBoard freeBoard = new FreeBoard();
            freeBoard.addBoard(user1);
            freeBoardRepository.save(freeBoard);
        }
    }

    private AdminBoard createBoard() {
        AdminBoard adminBoard = new AdminBoard();
        return adminBoardRepository.save(adminBoard);
    }

    private Comment createComment(User user, Board board) {
        Comment comment = new Comment();
        comment.addComment(user, board, "sample");
        return commentRepository.save(comment);
    }

    private Comment createCommentReply(User user, Board board, Reply reply) {
        Comment comment = new Comment();
        comment.addComment(user, board, "sample");
        reply.addReply(comment, "댓글1", user);
        return commentRepository.save(comment);
    }

    private Like createLike(User user, Board board) {
        Like like = new Like();
        like.addLike(user, board);
        return likeRepository.save(like);
    }

    private void clearContext() {
        em.flush();
        em.clear();
    }

}
