package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.User;

@Entity
@Getter
public class Lounge extends Board {

    @Id @GeneratedValue
    @Column(name = "lounge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    //연관 관계 메서드
    public void createLounge(User user) {
        this.user = user;
        user.getLounges().add(this);
    }
}
