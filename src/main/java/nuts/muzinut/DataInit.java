package nuts.muzinut;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.dto.security.AuthorityDto;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.board.RecruitBoardRepository;
import nuts.muzinut.repository.board.CommentRepository;
import nuts.muzinut.repository.board.LikeRepository;
import nuts.muzinut.repository.board.ReplyRepository;
import nuts.muzinut.repository.board.query.BoardQueryRepository;
import nuts.muzinut.repository.member.AuthorityRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.DataInitService;
import nuts.muzinut.service.board.AdminBoardService;
import nuts.muzinut.service.security.UserService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserService userService;
    private final AdminBoardRepository adminBoardRepository;
    private final RecruitBoardRepository recruitBoardRepository;
    private final BoardQueryRepository repository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final AdminBoardService adminBoardService;
    private final ReplyRepository replyRepository;

    private final DataInitService dataInitService;

    @PersistenceContext
    EntityManager em;


    @PostConstruct
    public void init() {
        AuthorityDto authorityDto = new AuthorityDto("admin");
        UserDto userDto = new UserDto("admin@naver.com", "admin", "add!");
        userService.adminSignup(userDto);
        UserDto userDto2 = new UserDto("user@naver.com", "user", "user!");
        userService.signup(userDto2);
        UserDto userDto3 = new UserDto("user2@naver.com", "user2", "user2!");
        userService.signup(userDto3);

        dataInitService.recruitBoardBoardScenario();
        dataInitService.commentScenario();
    }

//    @PostConstruct
    public void adminBoardScenario() {
        User user1 = new User();
        user1.setNickname("tom");
        User user2 = new User();
        user2.setNickname("nick");

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
    }

    private void createAdminBoards() {
        for (int i = 0; i < 20; i++) {
            AdminBoard adminBoard = new AdminBoard();
            adminBoardRepository.save(adminBoard);
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
