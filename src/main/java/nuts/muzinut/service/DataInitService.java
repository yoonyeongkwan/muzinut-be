package nuts.muzinut.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.board.Reply;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.repository.board.*;
import nuts.muzinut.repository.board.query.BoardQueryRepository;
import nuts.muzinut.repository.member.AuthorityRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.board.AdminBoardService;
import nuts.muzinut.service.security.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataInitService {

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

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void recruitBoardBoardScenario() {
        User user = userRepository.findByNickname("user!").orElseThrow(() -> new IllegalArgumentException("User not found"));
//        Hibernate.initialize(user.getRecruitBoards()); // 지연 로딩 초기화

        User user1 = new User();
        user1.setNickname("tom");
        User user2 = new User();
        user2.setNickname("nick");

        RecruitBoard recruitBoard = new RecruitBoard(user, "Sample Content", 5,
                LocalDateTime.now(), LocalDateTime.now().plusDays(7),
                LocalDateTime.now().plusDays(8), LocalDateTime.now().plusDays(14),
                "Sample Title");

        Comment comment1 = new Comment();
        comment1.addComment(user1, recruitBoard, "sample");
        Reply reply1 = new Reply();
        reply1.addReply(comment1, "댓글1", user1);

        Comment comment2 = new Comment();
        comment2.addComment(user1, recruitBoard, "sample");
        Reply reply2 = new Reply();
        reply2.addReply(comment1, "댓글2", user1);
        replyRepository.save(reply2);
    }

    @Transactional
    public void commentScenario() {
        // 사용자 생성
        User user = userRepository.findByNickname("user!").orElseThrow(() -> new IllegalArgumentException("User not found"));
        log.info("User found: {}", user.getNickname());

        // 게시판 생성
        RecruitBoard recruitBoard = new RecruitBoard(user, "Sample Content for Comment Test", 5,
                LocalDateTime.now(), LocalDateTime.now().plusDays(7),
                LocalDateTime.now().plusDays(8), LocalDateTime.now().plusDays(14),
                "Sample Title for Comment Test");
        recruitBoardRepository.save(recruitBoard);
        log.info("RecruitBoard created: {}", recruitBoard.getTitle());
    }
}
