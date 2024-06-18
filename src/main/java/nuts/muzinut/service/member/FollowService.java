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

    // 특정 유저의 `팔로잉` 수를 반환합니다.
    /**
     * @param user: 팔로잉 수를 알고 싶은 유저
     * @return 팔로잉 수
     */
    public Long countFollowing(User user) {
        return followRepository.countFollowingByUser(user);
    }

    // 특정 유저의 `팔로워` 수를 반환합니다.
    /**
     * @param followingMemberId: 팔로워 수를 알고 싶은 유저의 ID
     * @return 팔로워 수
     */
    public Long countFollowers(Long followingMemberId) {
        return followRepository.countFollowerByFollowingMemberId(followingMemberId);
    }

    // 팔로우 알림을 끕니다.
    /**
     * @param user: 알림을 끄는 것을 희망하는 회원
     * @param followingMemberId: 알림을 끄고 싶은 대상
     */
    @Transactional
    public void turnOffNotification(User user, Long followingMemberId) {
        followRepository.turnOffNotification(false, user, followingMemberId);
    }

    // 팔로우 알림을 켭니다.
    /**
     * @param user: 알림을 켜는 것을 희망하는 회원
     * @param followingMemberId: 알림을 켜고 싶은 대상
     */
    @Transactional
    public void turnOnNotification(User user, Long followingMemberId) {
        followRepository.turnOnNotification(true, user, followingMemberId);
    }

    // 특정 유저가 팔로우한 모든 팔로우 정보를 반환합니다.
    /**
     * @param user: 팔로우 정보를 알고 싶은 유저
     * @return 팔로우 정보 리스트
     */
    public List<Follow> getFollowingList(User user) {
        return followRepository.findByUser(user);
    }
}
