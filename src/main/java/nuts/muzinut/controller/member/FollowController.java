package nuts.muzinut.controller.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.FollowDto;
import nuts.muzinut.service.member.FollowService;
import nuts.muzinut.service.security.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserService userService; // UserService를 통해 User 객체를 가져오도록 수정

    @GetMapping("/following-count")
    public ResponseEntity<Long> getFollowingCount(@RequestParam Long userId) {
        log.info("Getting following count for user with ID: {}", userId);
        User user = userService.findUserById(userId); // UserService를 통해 User 객체를 가져옴
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Long count = followService.countFollowing(user);
        return ResponseEntity.ok(count); // 응답을 명확하게 하기 위해 ResponseEntity를 사용하여 HTTP 상태 코드와 함께 응답을 반환.
    }

    @GetMapping("/followers-count")
    public ResponseEntity<Long> getFollowersCount(@RequestParam Long userId) {
        log.info("Getting followers count for user with ID: {}", userId);
        User user = userService.findUserById(userId); // UserService를 통해 User 객체를 가져옴
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Long count = followService.countFollowers(userId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/turn-off-notification")
    public ResponseEntity<String> turnOffNotification(@RequestBody @Valid FollowDto followDto) {
        log.info("Turning off notification for user ID: {} and following member ID: {}", followDto.getUserId(), followDto.getFollowingMemberId());
        User user = userService.findUserById(followDto.getUserId()); // UserService를 통해 User 객체를 가져옴
        if (user == null) {
            log.error("User not found with ID: {}", followDto.getUserId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        followService.turnOffNotification(user, followDto.getFollowingMemberId());
        return ResponseEntity.ok("Notification turned off");
    }

    @PostMapping("/turn-on-notification")
    public ResponseEntity<String> turnOnNotification(@RequestBody @Valid FollowDto followDto) {
        log.info("Turning on notification for user ID: {} and following member ID: {}", followDto.getUserId(), followDto.getFollowingMemberId());
        User user = userService.findUserById(followDto.getUserId()); // UserService를 통해 User 객체를 가져옴
        if (user == null) {
            log.error("User not found with ID: {}", followDto.getUserId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        followService.turnOnNotification(user, followDto.getFollowingMemberId());
        return ResponseEntity.ok("Notification turned on");
    }

    @GetMapping("/following-list")
    public ResponseEntity<List<Follow>> getFollowingList(@RequestParam Long userId) {
        log.info("Getting following list for user with ID: {}", userId);
        User user = userService.findUserById(userId); // UserService를 통해 User 객체를 가져옴
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Follow> followingList = followService.getFollowingList(user);
        return ResponseEntity.ok(followingList);
    }
}
