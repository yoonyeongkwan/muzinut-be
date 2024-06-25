package nuts.muzinut.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.board.Reply;
import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.dto.security.AuthorityDto;
import nuts.muzinut.repository.board.*;
import nuts.muzinut.repository.board.query.BoardQueryRepository;
import nuts.muzinut.repository.member.AuthorityRepository;
import nuts.muzinut.repository.member.FollowRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.board.AdminBoardService;
import nuts.muzinut.service.member.FollowService;
import nuts.muzinut.service.security.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataInitService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final RecruitBoardRepository recruitBoardRepository;
    private final ReplyRepository replyRepository;
    private final FollowRepository followRepository;
    private final FollowService followService;

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void initializeData() {
        AuthorityDto authorityDto = new AuthorityDto("admin");
        UserDto userDto = new UserDto("admin@naver.com", "admin", "add!");
        userService.adminSignup(userDto);
        UserDto userDto2 = new UserDto("user@naver.com", "user", "user!");
        userService.signup(userDto2);
        UserDto userDto3 = new UserDto("user2@naver.com", "user2", "user2!");
        userService.signup(userDto3);
        UserDto userDto4 = new UserDto("user3@naver.com", "user3", "user3!");
        userService.signup(userDto4);

//        recruitBoardBoardScenario();
//        commentScenario();
//        followScenario();
    }

    private void followScenario() {
        // 사용자 불러오기
        User user1 = userRepository.findByNickname("user!").orElseThrow(() -> new IllegalArgumentException("User not found"));
        User user2 = userRepository.findByNickname("user2!").orElseThrow(() -> new IllegalArgumentException("User not found"));
        User user3 = userRepository.findByNickname("user3!").orElseThrow(() -> new IllegalArgumentException("User not found"));

        // FollowService를 사용하여 팔로우 관계 설정
        followService.followUser(user1, user3.getId());
        log.info("{}가 {}를 팔로우", user1.getNickname(), user3.getNickname());

        followService.followUser(user2, user3.getId());
        log.info("{}가 {}를 팔로우", user2.getNickname(), user3.getNickname());

        // 알림 기능 테스트
        followRepository.updateNotificationStatus(false, user1, user3.getId());
        log.info("{}의 {} 팔로우 알림을 끕니다.", user1.getNickname(), user3.getNickname());

        followRepository.updateNotificationStatus(true, user1, user3.getId());
        log.info("{}의 {} 팔로우 알림을 켭니다.", user1.getNickname(), user3.getNickname());
    }

    @Transactional
    public void recruitBoardBoardScenario() {
        User user = userRepository.findByNickname("user2!").orElseThrow(() -> new IllegalArgumentException("User not found"));
//        Hibernate.initialize(user.getRecruitBoards()); // 지연 로딩 초기화
//        System.out.println("user = "+user);
        log.info("User found: {}", user);

        User user1 = new User();
        user1.setNickname("tom");
        User user2 = new User();
        user2.setNickname("nick");

        RecruitBoard recruitBoard = new RecruitBoard(user, "Sample Content", 5,
                LocalDateTime.now(), LocalDateTime.now().plusDays(7),
                LocalDateTime.now().plusDays(8), LocalDateTime.now().plusDays(14),
                "Sample Title");

        recruitBoardRepository.save(recruitBoard);

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
