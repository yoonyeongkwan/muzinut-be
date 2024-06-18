package nuts.muzinut.repository.member;

import nuts.muzinut.domain.member.Friend;
import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    /**
     * @param isFriend: 친구 여부 (수락은 true, 거절은 false)
     * @param user: 친구 요청을 보낸 사람
     */
    @Modifying
    @Query("update Friend f set f.isFriend = :isFriend where f.user = :user")
    void chooseFriendOffer(@Param("isFriend") Boolean isFriend, @Param("user") User user);

}
