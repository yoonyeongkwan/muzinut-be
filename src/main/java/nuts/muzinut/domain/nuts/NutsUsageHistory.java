package nuts.muzinut.domain.nuts;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "nuts_usage_history")
public class NutsUsageHistory {

    @Id @GeneratedValue
    @Column(name = "nuts_usage_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "used_nuts_count")
    private int usedNutsCount;

    @Column(name = "used_dt")
    private LocalDateTime usedDt;

    @Column(name = "used_content")
    private String usedContent;

    @Column(name = "used_channel")
    private String usedChannel; //enum 으로 변환될 수 도 있음

    @Column(name = "support_msg")
    private String supportMsg;

    //연관 관계 편의 메서드
    public void createNutsHistory(Member member) {
        this.member = member;
        member.getNutsUsageHistories().add(this);
    }
}
