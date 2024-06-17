package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class Mailbox {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mailbox_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String message;
    private Boolean isChecked = false;

     //연관관계 편의 메서드
    public void createMailbox(User user) {
        this.user = user;
        user.getMailboxes().add(this);
    }

    public void createMailbox(User user, String message) {
        this.user = user;
        this.message = message;
        user.getMailboxes().add(this);
    }
}
