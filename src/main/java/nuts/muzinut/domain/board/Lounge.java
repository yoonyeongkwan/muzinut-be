package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.Member;

import java.time.LocalDateTime;

@Entity
@Getter
public class Lounge extends BaseBoardEntity {

    @Id @GeneratedValue
    @Column(name = "lounge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    //연관 관계 메서드
    public void createLounge(Member member) {
        this.member = member;
        this.setCreated_dt(LocalDateTime.now());
        this.setModified_dt(LocalDateTime.now());
        member.getLounges().add(this);
    }
}
