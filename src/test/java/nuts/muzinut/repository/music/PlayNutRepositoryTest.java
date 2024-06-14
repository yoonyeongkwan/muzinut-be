package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.PlayNut;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PlayNutRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PlayNutRepository playNutRepository;

    @Test
    void save() {

        //given
        User user = new User();
        userRepository.save(user);

        PlayNut playNut = new PlayNut();
        playNut.createPleNut(user);

        //when
        playNutRepository.save(playNut);

        //then
        Optional<PlayNut> findPleNut = playNutRepository.findById(playNut.getId());
        assertThat(findPleNut.get()).isEqualTo(playNut);
        assertThat(findPleNut.get().getUser()).isEqualTo(user);
    }

    @Test
    void delete() {

        //given
        PlayNut playNut = new PlayNut();
        playNutRepository.save(playNut);

        //when
        playNutRepository.delete(playNut);

        //then
        Optional<PlayNut> findPleNut = playNutRepository.findById(playNut.getId());
        assertThat(findPleNut.isEmpty()).isTrue();
    }

    //회원이 삭제되면 해당 회원의 플리넛도 삭제되어야 한다.
    @Test
    void deleteMember() {

        //given
        User user = new User();
        userRepository.save(user);

        PlayNut playNut = new PlayNut();
        playNut.createPleNut(user);
        playNutRepository.save(playNut);

        //when
        userRepository.delete(user);

        //then
        Optional<PlayNut> findPleNut = playNutRepository.findById(playNut.getId());
        assertThat(findPleNut.isEmpty()).isTrue();
    }
}