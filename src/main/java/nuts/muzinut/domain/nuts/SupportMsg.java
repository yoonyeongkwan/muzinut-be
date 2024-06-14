package nuts.muzinut.domain.nuts;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.User;

@Entity
@Getter
@Table(name = "support_msg")
public class SupportMsg {

    @Id @GeneratedValue
    @Column(name = "support_msg_id")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "sponsor_id")
    private Long sponsorId; //후원한 팬 pk

    private String message;

    /**
     * 연관관계 편의 메서드
     * @param user:  후원 받는 회원
     * @param sponsor: 후원하는 팬
     * @param message: 후원 메시지
     * @param nuts:    후원 nuts
     */
    public void addSupportMsg(User user, User sponsor, String message, int nuts) {
        this.user = user;
        this.sponsorId = sponsor.getId();
        this.message = message;
        user.getSupportMsgs().add(this);
    }
}
