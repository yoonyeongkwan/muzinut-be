package nuts.muzinut.repository.nuts;

import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.nuts.PaymentHistory;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PaymentHistoryRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PaymentHistoryRepository paymentHistoryRepository;

    @Test
    void save() {

        //given
        User user = new User();
        userRepository.save(user);

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.newPayment(user, 10000);

        //when
        paymentHistoryRepository.save(paymentHistory);

        //then
        Optional<PaymentHistory> findPaymentHistory = paymentHistoryRepository.findById(paymentHistory.getId());
        assertThat(findPaymentHistory.get()).isEqualTo(paymentHistory);
        assertThat(findPaymentHistory.get().getUser()).isEqualTo(user);
        assertThat(findPaymentHistory.get().getChargeAmount()).isEqualTo(10000); //충전 금액
    }

    @Test
    void delete() {

        //given
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistoryRepository.save(paymentHistory);

        //when
        paymentHistoryRepository.delete(paymentHistory);

        //then
        Optional<PaymentHistory> findPaymentHistory = paymentHistoryRepository.findById(paymentHistory.getId());
        assertThat(findPaymentHistory.isEmpty()).isTrue();
    }

    //회원이 삭제되면 해당 회원의 충전 내역도 삭제 (논의 필요)
    @Test
    void deleteMember() {

        //given
        User user = new User();
        userRepository.save(user);

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.newPayment(user, 10000);
        paymentHistoryRepository.save(paymentHistory);

        //when
        userRepository.delete(user);

        //then
        Optional<PaymentHistory> findPaymentHistory = paymentHistoryRepository.findById(paymentHistory.getId());
        assertThat(findPaymentHistory.isEmpty()).isTrue();
    }
}