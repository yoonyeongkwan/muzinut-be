package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Album;
import nuts.muzinut.domain.music.Music;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumRepositoryTest {

    @Autowired MusicRepository musicRepository;
    @Autowired AlbumRepository albumRepository;

    @Test
    void save() {

        //given
        Music music = new Music();
        musicRepository.save(music);

        Album album = new Album();
        album.addSongIntoAlbum(music);

        //when
        albumRepository.save(album);

        //then
        Optional<Album> findAlbum = albumRepository.findById(album.getId());
        assertThat(findAlbum.get()).isEqualTo(album);
        assertThat(findAlbum.get().getMusicList().size()).isEqualTo(1); //앨범에 수록된 음악은 1개
    }

    //하나의 앨범에 여러 곡을 저장
    @Test
    void saveMultipleMusic() {

        //given
        Music music1 = new Music();
        Music music2 = new Music();
        musicRepository.save(music1);
        musicRepository.save(music2);

        Album album = new Album();
        //앨범에 2개의 곡 저장
        album.addSongIntoAlbum(music1);
        album.addSongIntoAlbum(music2);
        albumRepository.save(album);

        //when
        albumRepository.save(album);

        //then
        Optional<Album> findAlbum = albumRepository.findById(album.getId());
        assertThat(findAlbum.get()).isEqualTo(album);

        //앨범에 수록된 음악은 2개
        assertThat(findAlbum.get().getMusicList().size()).isEqualTo(2);
        assertThat(findAlbum.get().getMusicList())
                .extracting("id")
                .contains(music1.getId(), music2.getId()); //pk 를 직접 비교
    }

    @Test
    void delete() {

        //given
        Album album = new Album();
        albumRepository.save(album);

        //when
        albumRepository.delete(album);

        //then
        Optional<Album> findAlbum = albumRepository.findById(album.getId());
        assertThat(findAlbum.isEmpty()).isTrue();
    }
}