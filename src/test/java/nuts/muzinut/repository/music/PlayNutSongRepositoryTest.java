package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.domain.music.PlayNut;
import nuts.muzinut.domain.music.PlayNutMusic;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PlayNutSongRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PlayNutRepository playNutRepository;
    @Autowired
    MusicRepository musicRepository;
    @Autowired
    PlayNutMusicRepository playNutMusicRepository;

    @Test
    void save() {

        //given
        PlayNut playNut = new PlayNut();
        playNutRepository.save(playNut);

        Song song = new Song();
        musicRepository.save(song);

        PlayNutMusic playNutMusic = new PlayNutMusic();
        playNutMusic.addPlaylistMusic(playNut, song);

        //when
        playNutMusicRepository.save(playNutMusic);

        //then
        Optional<PlayNutMusic> findPleNutMusic = playNutMusicRepository.findById(playNutMusic.getId());
        assertThat(findPleNutMusic.get()).isEqualTo(playNutMusic);
        assertThat(findPleNutMusic.get().getPlayNut()).isEqualTo(playNut);
    }

    @Test
    void delete() {

        //given
        PlayNutMusic playNutMusic = new PlayNutMusic();
        playNutMusicRepository.save(playNutMusic);

        //when
        playNutMusicRepository.delete(playNutMusic);

        //then
        Optional<PlayNutMusic> findPleNutMusic = playNutMusicRepository.findById(playNutMusic.getId());
        assertThat(findPleNutMusic.isEmpty()).isTrue();
    }

    //pleNut 을 삭제하면 해당 pleNut 의 pleNutMusic 모두 삭제
    @Test
    void deletePleNut() {

        //given
        PlayNut playNut = new PlayNut();
        playNutRepository.save(playNut);

        Song song = new Song();
        musicRepository.save(song);

        PlayNutMusic playNutMusic = new PlayNutMusic();
        playNutMusic.addPlaylistMusic(playNut, song);
        playNutMusicRepository.save(playNutMusic);

        //when
        playNutRepository.delete(playNut);

        //then
        Optional<PlayNutMusic> findPleNutMusic = playNutMusicRepository.findById(playNutMusic.getId());
        assertThat(findPleNutMusic.isEmpty()).isTrue();
    }

    //회원을 삭제하면 해당 회원의 pleNut 과 pleNutMusic 모두 삭제
    @Test
    void deleteMember() {

        //given
        User user = new User();
        userRepository.save(user);

        PlayNut playNut = new PlayNut();
        playNut.createPleNut(user);
        playNutRepository.save(playNut);

        Song song = new Song();
        musicRepository.save(song);

        PlayNutMusic playNutMusic = new PlayNutMusic();
        playNutMusic.addPlaylistMusic(playNut, song);
        playNutMusicRepository.save(playNutMusic);

        //when
        userRepository.delete(user);

        //then
        Optional<PlayNutMusic> findPleNutMusic = playNutMusicRepository.findById(playNutMusic.getId());
        assertThat(findPleNutMusic.isEmpty()).isTrue();

        Optional<PlayNut> findPleNut = playNutRepository.findById(playNut.getId());
        assertThat(findPleNut.isEmpty()).isTrue();
    }
}