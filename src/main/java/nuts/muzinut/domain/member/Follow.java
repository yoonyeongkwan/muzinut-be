package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;

@Entity
@Getter
public class Follow {

    @Id @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //팔로잉을 하는 주체

    @Column(name = "following_member_id")
    private Long followingMemberId; //팔로잉 하는 대상
    private Boolean notification;

    /**
     * 연관 관계 편의 메서드
     * @param user: 팔로잉 하는 주체
     * @param followingUser: 팔로잉 하는 대상
     */
    public void createFollowing(User user, User followingUser) {
        this.user = user;
        this.followingMemberId = followingUser.getId();
        this.notification = true; //처음 팔로우 했을 때 알림 설정은 기본적으로 켜져있음
        // 방어 코드 추가
        if (user.getFollowings() == null) {
            user.setFollowings(new ArrayList<>());
        }

        user.getFollowings().add(this);
    }

    //비지니스 로직
    public void turnOffNotification() {
        this.notification = false;
    }

    public void turnOnNotification() {
        this.notification = true;
    }
}
