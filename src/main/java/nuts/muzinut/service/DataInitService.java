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
import nuts.muzinut.dto.security.AuthorityDto;
import nuts.muzinut.repository.board.*;
import nuts.muzinut.repository.member.FollowRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.member.FollowService;
import nuts.muzinut.service.member.UserService;
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
        UserDto userDto2 = new UserDto("user@naver.com", "user", "maeng");
        userService.signup(userDto2);
        UserDto userDto3 = new UserDto("user2@naver.com", "user2", "juyoung");
        userService.signup(userDto3);
        UserDto userDto4 = new UserDto("user3@naver.com", "user3", "muzinut");
        userService.signup(userDto4);

        recruitBoardBoardScenario();
        followScenario();
    }

    private void followScenario() {
        // 사용자 불러오기
        User user1 = userRepository.findByNickname("maeng").orElseThrow(() -> new IllegalArgumentException("User not found"));
        User user2 = userRepository.findByNickname("juyoung").orElseThrow(() -> new IllegalArgumentException("User not found"));
        User user3 = userRepository.findByNickname("muzinut").orElseThrow(() -> new IllegalArgumentException("User not found"));

        // FollowService를 사용하여 팔로우 관계 설정
        followService.toggleFollow(user1, user3.getId());
        log.info("{}가 {}를 팔로우", user1.getNickname(), user3.getNickname());

        followService.toggleFollow(user2, user3.getId());
        log.info("{}가 {}를 팔로우", user2.getNickname(), user3.getNickname());

        // 알림 기능 테스트
        followService.toggleNotification(user1, user3.getId());
        log.info("{}의 {} 팔로우 알림을 끕니다.", user1.getNickname(), user3.getNickname());

        followService.toggleNotification(user1, user3.getId());
        log.info("{}의 {} 팔로우 알림을 켭니다.", user1.getNickname(), user3.getNickname());

        // 데이터베이스 커밋 보장
        em.flush();
    }

    @Transactional
    public void recruitBoardBoardScenario() {
        User user = userRepository.findByNickname("maeng").orElseThrow(() -> new IllegalArgumentException("User not found"));
        User user2 = userRepository.findByNickname("juyoung").orElseThrow(() -> new IllegalArgumentException("User not found"));
        User user3 = userRepository.findByNickname("muzinut").orElseThrow(() -> new IllegalArgumentException("User not found"));
        log.info("User found: {}", user);

        RecruitBoard recruitBoard = new RecruitBoard(user, "Sample Content", 5,
                LocalDateTime.now(), LocalDateTime.now().plusDays(7),
                LocalDateTime.now().plusDays(8), LocalDateTime.now().plusDays(14),
                "Sample Title");

        recruitBoardRepository.save(recruitBoard);

        Comment comment1 = new Comment();
        comment1.addComment(user, recruitBoard, "sample");
        Reply reply1 = new Reply();
        reply1.addReply(comment1, "대댓글1", user2);

        Comment comment2 = new Comment();
        comment2.addComment(user3, recruitBoard, "sample2");
        Reply reply2 = new Reply();
        reply2.addReply(comment1, "대댓글2", user2);
        replyRepository.save(reply2);
    }
}
