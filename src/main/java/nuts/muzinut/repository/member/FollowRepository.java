package nuts.muzinut.repository.member;

import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 특정 유저가 특정 회원을 팔로우하고 있는지 확인하는 메서드
     * @param user: 팔로잉 하는 주체
     * @param followingMemberId: 팔로잉 하는 대상
     * @return 팔로우 여부
     */
    boolean existsByUserAndFollowingMemberId(User user, Long followingMemberId);

    /**
     * 특정 유저의 팔로워 리스트를 가져오는 메서드
     * @param followingMemberId: 팔로잉 하는 대상의 ID
     * @return 팔로워 리스트
     */
    List<Follow> findByFollowingMemberId(Long followingMemberId);

    /**
     * 특정 유저가 팔로우한 회원의 리스트를 가져오는 메서드
     * @param user: 팔로잉 하는 주체
     * @return 팔로잉 리스트
     */
    @Query("select f.followingMemberId from Follow f where f.user = :user")
    List<Long> findFollowingMemberIdsByUser(User user);

    /**
     * 특정 유저가 특정 회원을 팔로우하는 메서드
     * @param user: 팔로잉 하는 주체
     * @param followingMemberId: 팔로잉 하는 대상
     */
    @Modifying
    @Transactional
    @Query("insert into Follow (user, followingMemberId, notification) values (:user, :followingMemberId, true)")
    void follow(User user, Long followingMemberId);

    /**
     * 특정 유저가 특정 회원을 팔로우 취소하는 메서드
     * @param user: 팔로잉 취소하는 주체
     * @param followingMemberId: 팔로잉 취소하는 대상
     */
    @Modifying
    @Transactional
    @Query("delete from Follow f where f.user = :user and f.followingMemberId = :followingMemberId")
    void unfollow(User user, Long followingMemberId);
}
