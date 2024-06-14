package nuts.muzinut.domain.nuts;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "nuts_usage_history")
public class NutsUsageHistory {

    @Id @GeneratedValue
    @Column(name = "nuts_usage_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "used_nuts_count")
    private int usedNutsCount; //너츠 사용 개수

    @Column(name = "used_dt")
    private LocalDateTime usedDt; //너츠 사용 일자

    @Column(name = "used_content")
    private String usedContent; //사용 내역 ex) 투표권 구매, 아티스트 후원

    @Column(name = "used_channel")
    private String usedChannel; //enum 으로 변환될 수 도 있음

    @Column(name = "support_msg")
    private String supportMsg; //후원 메시지

    //연관 관계 편의 메서드
    public void createNutsHistory(User user) {
        this.user = user;
        user.getNutsUsageHistories().add(this);
    }
}
