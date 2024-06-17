package nuts.muzinut.repository.member;

import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Long countFollowingByUser(User user); //특정 유저의 팔로잉 수

    Long countFollowerByFollowingMemberId(Long followingMemberId); //나의 팔로워 수

    /**
     * @param notification: 팔로잉 한 사람의 알람을 끄는 쿼리이므로 항상 false
     * @param user: 알림을 끄는 것을 희망하는 회원
     * @param followingMemberId: 알림을 끄고 싶은 대상
     */
    @Modifying
    @Query("update Follow f set f.notification = :notification where f.user = :user and f.followingMemberId = :followingMemberId")
    void turnOffNotification(Boolean notification, User user, Long followingMemberId);

    /**
     * @param notification: 팔로잉 한 사람의 알람을 켜는 쿼리이므로 항상 true
     * @param user: 알림을 켜는 것을 희망하는 회원
     * @param followingMemberId: 알림을 켜고 싶은 대상
     */
    @Modifying
    @Query("update Follow f set f.notification = :notification where f.user = :user and f.followingMemberId = :followingMemberId")
    void turnOnNotification(Boolean notification, User user, Long followingMemberId);

    List<Follow> findByUser(User user);
}
