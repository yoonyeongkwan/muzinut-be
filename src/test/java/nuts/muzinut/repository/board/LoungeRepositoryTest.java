package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Lounge;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class LoungeRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoungeRepository loungeRepository;

    @Test
    void save() {

        //given
        User user = new User();
        userRepository.save(user);

        Lounge lounge = new Lounge();
        lounge.createLounge(user);

        //when
        loungeRepository.save(lounge);

        //then
        Optional<Lounge> findLounge = loungeRepository.findById(lounge.getId());
        assertThat(findLounge.get()).isEqualTo(lounge);
        assertThat(findLounge.get().getUser()).isEqualTo(user);
    }

    @Test
    void delete() {

        //given
        Lounge lounge = new Lounge();
        loungeRepository.save(lounge);

        //when
        loungeRepository.delete(lounge);

        //then
        Optional<Lounge> findLounge = loungeRepository.findById(lounge.getId());
        assertThat(findLounge.isEmpty()).isTrue();
    }
}