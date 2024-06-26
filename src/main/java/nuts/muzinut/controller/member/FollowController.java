package nuts.muzinut.controller.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.member.follow.FollowDto;
import nuts.muzinut.dto.member.follow.FollowListDto;
import nuts.muzinut.service.member.FollowService;
import nuts.muzinut.service.security.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
//@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    // 팔로우 토글
    @PreAuthorize("hasRole('USER')")
    @PostMapping("follow")
    public ResponseEntity<MessageDto> toggleFollow(@RequestBody @Valid FollowDto followDto) {
        User user = getAuthenticatedUser();
        return followService.toggleFollow(user, followDto.getFollowingMemberId());
    }

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
    public ResponseEntity<Map<String, Long>> getFollowingCount(@RequestParam(value = "userId") Long userId) {
        log.info("Getting following count for user with ID: {}", userId);
        User user = userService.findUserById(userId);
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Long count = followService.countFollowing(user);
        Map<String, Long> response = new HashMap<>();
        response.put("followingCount", count);
        return ResponseEntity.ok(response);
    }

    // 특정 유저의 `팔로워` 수를 반환하는 메소드
    @GetMapping("/followers-count")
    public ResponseEntity<Map<String, Long>> getFollowersCount(@RequestParam(value = "userId") Long userId) {
        log.info("Getting followers count for user with ID: {}", userId);
        User user = userService.findUserById(userId);
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Long count = followService.countFollowers(userId);
        Map<String, Long> response = new HashMap<>();
        response.put("followersCount", count);
        return ResponseEntity.ok(response);
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

    // 팔로잉(특정 유저가 팔로우한 회원의) 리스트를 반환하는 메소드
    @GetMapping("/following-list")
    public ResponseEntity<List<FollowListDto>> getFollowingList(@RequestParam(value = "userId") Long userId) {
        log.info("Getting following list for user with ID: {}", userId);
        User user = userService.findUserById(userId);
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<FollowListDto> followingList = followService.getFollowingList(user);
        if (followingList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(followingList);
        }
        return ResponseEntity.ok(followingList);
    }

    // 팔로워(특정 유저를 팔로우한 회원의) 리스트를 반환하는 메소드
    @GetMapping("/followers-list")
    public ResponseEntity<List<FollowListDto>> getFollowersList(@RequestParam(value = "userId") Long userId) {
        log.info("Getting followers list for user with ID: {}", userId);
        User user = userService.findUserById(userId);
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<FollowListDto> followersList = followService.getFollowerList(userId);
        if (followersList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(followersList);
        }
        return ResponseEntity.ok(followersList);
    }
}
