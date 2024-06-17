package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class SongRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MusicRepository musicRepository;

    @Test
    void save() {

        //given
        User user = new User();
        userRepository.save(user);
        Song song = new Song();
        song.createMusic(user);

        //when
        musicRepository.save(song);

        //then
        Optional<Song> findMusic = musicRepository.findById(song.getId());
        assertThat(findMusic.get()).isEqualTo(song);
        assertThat(findMusic.get()).isEqualTo(user.getSongList().getFirst());
    }

    @Test
    void delete() {

        //given
        Song song = new Song();
        musicRepository.save(song);

        //when
        musicRepository.delete(song);

        //then
        Optional<Song> findMusic = musicRepository.findById(song.getId());
        assertThat(findMusic.isEmpty()).isTrue();

    }
}