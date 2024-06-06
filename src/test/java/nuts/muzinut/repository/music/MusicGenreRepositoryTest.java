package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Genre;
import nuts.muzinut.domain.music.Music;
import nuts.muzinut.domain.music.MusicGenre;
import nuts.muzinut.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MusicGenreRepositoryTest {

    @Autowired
    MusicRepository musicRepository;
    @Autowired
    MusicGenreRepository musicGenreRepository;


    @Test
    void save() {

        //given
        Music music = new Music();
        musicRepository.save(music);

        MusicGenre musicGenre1 = new MusicGenre(Genre.HIPHOP);
        MusicGenre musicGenre2 = new MusicGenre(Genre.RNB);

        //when: 하나의 음악에 2가지 장르를 넣을 때
        musicGenre1.addMusicGenre(music);
        musicGenre2.addMusicGenre(music);
        musicGenreRepository.save(musicGenre1);
        musicGenreRepository.save(musicGenre2);

        //then
        List<MusicGenre> result = musicGenreRepository.findAll();

        //1개의 음악만 저장되어 있음
        assertThat(result)
                .extracting("music")
                .containsOnly(music);

        //1개의 음악에 대한 장르는 힙합 & 알엔비
        assertThat(result)
                .extracting("genre")
                .contains(Genre.HIPHOP, Genre.RNB);
    }

    @Test
    void delete() {

        //given
        MusicGenre musicGenre = new MusicGenre(Genre.HIPHOP);
        musicGenreRepository.save(musicGenre);

        //when
        musicGenreRepository.delete(musicGenre);

        //then
        Optional<MusicGenre> result = musicGenreRepository.findById(musicGenre.getId());
        assertThat(result.isEmpty()).isTrue();
    }

    //음악을 삭제하면 해당 음악의 장르에 대한 정보도 모두 삭제되어야 한다
    @Test
    void deleteMusic() {

        //given
        Music music = new Music();
        musicRepository.save(music);

        MusicGenre musicGenre1 = new MusicGenre(Genre.HIPHOP);
        MusicGenre musicGenre2 = new MusicGenre(Genre.RNB);

        musicGenre1.addMusicGenre(music);
        musicGenre2.addMusicGenre(music);
        musicGenreRepository.save(musicGenre1);
        musicGenreRepository.save(musicGenre2);

        //when
        musicRepository.delete(music);

        //then
        List<MusicGenre> result = musicGenreRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }
}