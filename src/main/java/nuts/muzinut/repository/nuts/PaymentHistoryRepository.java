package nuts.muzinut.repository.nuts;

import nuts.muzinut.domain.nuts.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
}
