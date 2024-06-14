package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Mailbox {

    @Id @GeneratedValue
    @Column(name = "mailbox_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String message;
    private Boolean isChecked;

     //연관관계 편의 메서드
    public void createMailbox(User user) {
        this.user = user;
        user.getMailboxes().add(this);
    }
}
