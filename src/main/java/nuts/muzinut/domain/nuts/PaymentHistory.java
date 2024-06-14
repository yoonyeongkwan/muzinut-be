package nuts.muzinut.domain.nuts;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "payment_history")
public class PaymentHistory {

    @Id @GeneratedValue
    @Column(name = "payment_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "charge_amount")
    private int chargeAmount; //충전금액
    private LocalDateTime time; //충전 시간 or 환불 시간 ...

    @Enumerated(EnumType.STRING)
    @Column(name = "nuts_status")
    private NutsStatus nutsStatus;

    //연관관계 메서드
    public void newPayment(User user, int money) {
        this.user = user;
        this.chargeAmount = money;
        user.getPaymentHistories().add(this);
    }
}
