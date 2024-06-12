package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Genre;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.domain.music.SongGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class SongGenreRepositoryTest {

    @Autowired
    MusicRepository musicRepository;
    @Autowired
    MusicGenreRepository musicGenreRepository;


    @Test
    void save() {

        //given
        Song song = new Song();
        musicRepository.save(song);

        SongGenre songGenre1 = new SongGenre(Genre.HIPHOP);
        SongGenre songGenre2 = new SongGenre(Genre.RNB);

        //when: 하나의 음악에 2가지 장르를 넣을 때
        songGenre1.addMusicGenre(song);
        songGenre2.addMusicGenre(song);
        musicGenreRepository.save(songGenre1);
        musicGenreRepository.save(songGenre2);

        //then
        List<SongGenre> result = musicGenreRepository.findAll();

        //1개의 음악만 저장되어 있음
        assertThat(result)
                .extracting("song")
                .containsOnly(song);

        //1개의 음악에 대한 장르는 힙합 & 알엔비
        assertThat(result)
                .extracting("genre")
                .contains(Genre.HIPHOP, Genre.RNB);
    }

    @Test
    void delete() {

        //given
        SongGenre songGenre = new SongGenre(Genre.HIPHOP);
        musicGenreRepository.save(songGenre);

        //when
        musicGenreRepository.delete(songGenre);

        //then
        Optional<SongGenre> result = musicGenreRepository.findById(songGenre.getId());
        assertThat(result.isEmpty()).isTrue();
    }

    //음악을 삭제하면 해당 음악의 장르에 대한 정보도 모두 삭제되어야 한다
    @Test
    void deleteMusic() {

        //given
        Song song = new Song();
        musicRepository.save(song);

        SongGenre songGenre1 = new SongGenre(Genre.HIPHOP);
        SongGenre songGenre2 = new SongGenre(Genre.RNB);

        songGenre1.addMusicGenre(song);
        songGenre2.addMusicGenre(song);
        musicGenreRepository.save(songGenre1);
        musicGenreRepository.save(songGenre2);

        //when
        musicRepository.delete(song);

        //then
        List<SongGenre> result = musicGenreRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }
}