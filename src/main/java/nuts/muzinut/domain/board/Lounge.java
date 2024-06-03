package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.Member;

@Entity
@Getter
public class Lounge {

    @Id @GeneratedValue
    @Column(name = "lounge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    //연관 관계 메서드
    public void addLounge(Member member) {
        this.member = member;
        member.getLounges().add(this);
    }
}
