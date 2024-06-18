package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Friend {

    @Id @GeneratedValue
    @Column(name = "fried_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long friendId;
    private boolean isFriend; //두 유저가 서로 친구 관계에 있는지

    //연관 관계 메서드
    public void addFriend(User user, Long friendId) {
        this.user = user;
        this.friendId = friendId;
        user.getFriends().add(this);
    }
}
