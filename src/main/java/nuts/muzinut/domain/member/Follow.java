package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Follow {

    @Id @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //팔로잉을 하는 주체

    @Column(name = "following_member_id")
    private Long followingMemberId; //팔로잉 하는 대상
    private Boolean notification;

    /**
     * 연관 관계 편의 메서드
     * @param member: 팔로잉 하는 주체
     * @param followingMember: 팔로잉 하는 대상
     */
    public void createFollowing(Member member, Member followingMember) {
        this.member = member;
        this.followingMemberId = followingMember.getId();
        this.notification = true; //처음 팔로우 했을 때 알림 설정은 기본적으로 켜져있음
        member.getFollowings().add(this);
    }
}
