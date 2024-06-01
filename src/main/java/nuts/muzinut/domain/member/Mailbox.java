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
    @JoinColumn(name = "member_id")
    private Member member;

    private String message;
    private Boolean isChecked;

    /**
     * 연관관계 편의 메서드
     */
    public void createMailbox(Member member) {
        this.member = member;
        member.getMailboxes().add(this);
    }
}
