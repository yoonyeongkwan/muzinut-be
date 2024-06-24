package nuts.muzinut.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.member.FollowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    // 특정 유저의 `팔로잉` 수를 반환하는 메소드
    public Long countFollowing(User user) {
        validateUser(user);
        return followRepository.countFollowingByUser(user);
    }

    // 특정 유저의 `팔로워` 수를 반환하는 메소드
    public Long countFollowers(Long followingMemberId) {
        validateUserId(followingMemberId);
        return followRepository.countFollowerByFollowingMemberId(followingMemberId);
    }

    // 팔로우 알림을 끄는 메소드
    @Transactional
    public void turnOffNotification(User user, Long followingMemberId) {
        validateUser(user);
        validateUserId(followingMemberId);
        followRepository.turnOffNotification(false, user, followingMemberId);
    }

    // 팔로우 알림을 키는 메소드
    @Transactional
    public void turnOnNotification(User user, Long followingMemberId) {
        validateUser(user);
        validateUserId(followingMemberId);
        followRepository.turnOnNotification(true, user, followingMemberId);
    }

    // 특정 유저가 팔로우한 모든 팔로우 정보를 반환하는 메소드
    public List<Follow> getFollowingList(User user) {
        validateUser(user);
        return followRepository.findByUser(user);
    }

    // 특정 유저가 특정 회원을 팔로우하고 있는지 확인하는 메소드
    public boolean isFollowing(User user, Long followingMemberId) {
        validateUser(user);
        validateUserId(followingMemberId);
        return followRepository.existsByUserAndFollowingMemberId(user, followingMemberId);
    }

    // 특정 유저의 팔로워 리스트를 반환하는 메소드
    public List<Follow> getFollowers(Long followingMemberId) {
        validateUserId(followingMemberId);
        return followRepository.findByFollowingMemberId(followingMemberId);
    }

    // 특정 유저가 팔로우한 회원의 리스트를 반환하는 메소드
    public List<Long> getFollowingMemberIds(User user) {
        validateUser(user);
        return followRepository.findFollowingMemberIdsByUser(user);
    }

    // 유저 팔로우 메서드
    @Transactional
    public void follow(User user, Long followingMemberId) {
        validateUser(user);
        validateUserId(followingMemberId);
        if (!isFollowing(user, followingMemberId)) {
            followRepository.follow(user, followingMemberId);
        } else {
            throw new IllegalStateException("이미 팔로우 중입니다.");
        }
    }

    // 유저 팔로우 취소 메서드
    @Transactional
    public void unfollow(User user, Long followingMemberId) {
        validateUser(user);
        validateUserId(followingMemberId);
        if (isFollowing(user, followingMemberId)) {
            followRepository.unfollow(user, followingMemberId);
        } else {
            throw new IllegalStateException("팔로우 중이 아닙니다.");
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("유저 정보가 유효하지 않습니다.");
        }
    }

    private void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("유저 ID가 유효하지 않습니다.");
        }
    }
}
