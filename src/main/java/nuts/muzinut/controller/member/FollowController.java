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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
//@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    // 현재 인증된 사용자의 이름을 반환하는 메소드
    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    // 현재 인증된 사용자 객체를 반환하는 메소드
    private User getAuthenticatedUser() {
        String username = getCurrentUsername();
        return userService.findUserByUsername(username);
    }


    // 특정 유저의 `팔로잉` 수를 반환하는 메소드
    @GetMapping("/following-count")
    public ResponseEntity<Long> getFollowingCount(@RequestParam(value = "userId") Long userId) {
        log.info("Getting following count for user with ID: {}", userId);
        User user = userService.findUserById(userId);
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Long count = followService.countFollowing(user);
        return ResponseEntity.ok(count);
    }

    // 특정 유저의 `팔로워` 수를 반환하는 메소드
    @GetMapping("/followers-count")
    public ResponseEntity<Long> getFollowersCount(@RequestParam(value = "userId") Long userId) {
        log.info("Getting followers count for user with ID: {}", userId);
        Long count = followService.countFollowers(userId);
        return ResponseEntity.ok(count);
    }

    // 팔로우 알림을 끄는 메소드
    @PostMapping("/turn-off-notification")
    public ResponseEntity<String> turnOffNotification(@RequestBody @Valid FollowDto followDto) {
        log.info("Turning off notification for user ID: {} and following member ID: {}", followDto.getUserId(), followDto.getFollowingMemberId());
        User user = userService.findUserById(followDto.getUserId());
        if (user == null) {
            log.error("User not found with ID: {}", followDto.getUserId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        followService.turnOffNotification(user, followDto.getFollowingMemberId());
        return ResponseEntity.ok("Notification turned off");
    }

    // 팔로우 알림을 키는 메소드
    @PostMapping("/turn-on-notification")
    public ResponseEntity<String> turnOnNotification(@RequestBody @Valid FollowDto followDto) {
        log.info("Turning on notification for user ID: {} and following member ID: {}", followDto.getUserId(), followDto.getFollowingMemberId());
        User user = userService.findUserById(followDto.getUserId());
        if (user == null) {
            log.error("User not found with ID: {}", followDto.getUserId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        followService.turnOnNotification(user, followDto.getFollowingMemberId());
        return ResponseEntity.ok("Notification turned on");
    }

    // 특정 유저의 팔로워 리스트를 반환하는 메소드
    @GetMapping("/following-list")
    public ResponseEntity<List<Follow>> getFollowingList(@RequestParam Long userId) {
        log.info("Getting following list for user with ID: {}", userId);
        User user = userService.findUserById(userId);
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Follow> followingList = followService.getFollowingList(user);
        return ResponseEntity.ok(followingList);
    }

    // 특정 유저가 팔로우한 회원의 리스트를 반환하는 메소드
    @GetMapping("/followers-list")
    public ResponseEntity<List<Follow>> getFollowersList(@RequestParam Long userId) {
        log.info("Getting followers list for user with ID: {}", userId);
        List<Follow> followersList = followService.getFollowerList(userId);
        return ResponseEntity.ok(followersList);
    }

    // 유저 팔로우 메서드
    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestBody @Valid FollowDto followDto) {
        log.info("Following user ID: {} by user ID: {}", followDto.getFollowingMemberId(), followDto.getUserId());
        User user = getAuthenticatedUser();
        if (user == null) {
            log.error("Authenticated user not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        if (user.getId().equals(followDto.getFollowingMemberId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Users cannot follow themselves");
        }
        if (followService.isFollowing(user, followDto.getFollowingMemberId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already following this user");
        }
        followService.followUser(user, followDto.getFollowingMemberId());
        return ResponseEntity.ok("Followed successfully");
    }

    // 유저 팔로우 취소 메서드
    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody @Valid FollowDto followDto) {
        log.info("Unfollowing user ID: {} by user ID: {}", followDto.getFollowingMemberId(), followDto.getUserId());
        User user = getAuthenticatedUser();
        if (user == null) {
            log.error("Authenticated user not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        if (!followService.isFollowing(user, followDto.getFollowingMemberId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Not following this user");
        }
        followService.unfollowUser(user, followDto.getFollowingMemberId());
        return ResponseEntity.ok("Unfollowed successfully");
    }
}
