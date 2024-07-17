package nuts.muzinut.domain.mypick;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.User;

@Entity
@Getter
public class MypickComment extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "mypick_comment_id")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String comment;

    public void addMypcikComment(User user, String comment) {
        this.user = user;
        this.comment = comment;
    }
}
