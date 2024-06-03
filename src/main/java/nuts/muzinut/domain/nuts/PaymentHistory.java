package nuts.muzinut.domain.nuts;

import jakarta.persistence.*;
import nuts.muzinut.domain.member.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_history")
public class PaymentHistory {

    @Id @GeneratedValue
    @Column(name = "payment_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "charge_amount")
    private int chargeAmount; //충전금액
    private LocalDateTime time; //충전 시간 or 환불 시간 ...

    @Enumerated(EnumType.STRING)
    @Column(name = "nuts_status")
    private NutsStatus nutsStatus;

    //연관관계 메서드
    public void newPayment(Member member, int money) {
        this.member = member;
        this.chargeAmount = money;
        member.getPaymentHistories().add(this);
    }
}
