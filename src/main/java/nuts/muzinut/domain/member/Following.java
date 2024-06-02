package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Following {

    @Id @GeneratedValue
    @Column(name = "following_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; //팔로잉을 하는 주체

    private Long following_member_id; //팔로잉 하는 대상
    private Boolean notification;

    /**
     * 연관 관계 편의 메서드
     * @param member: 팔로잉 하는 주체
     * @param followingMember: 팔로잉 하는 대상
     */
    public void createFollowing(Member member, Member followingMember) {
        this.member = member;
        this.following_member_id = followingMember.getId();
        member.getFollowings().add(this);
    }
}
