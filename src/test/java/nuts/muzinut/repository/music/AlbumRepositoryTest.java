package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Album;
import nuts.muzinut.domain.music.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumRepositoryTest {

    @Autowired
    SongRepository musicRepository;
    @Autowired AlbumRepository albumRepository;

    @Test
    void save() {

        //given
        Song song = new Song();
        musicRepository.save(song);

        Album album = new Album();
        album.addSongIntoAlbum(song);

        //when
        albumRepository.save(album);

        //then
        Optional<Album> findAlbum = albumRepository.findById(album.getId());
        assertThat(findAlbum.get()).isEqualTo(album);
        assertThat(findAlbum.get().getSongList().size()).isEqualTo(1); //앨범에 수록된 음악은 1개
    }

    //하나의 앨범에 여러 곡을 저장
    @Test
    void saveMultipleMusic() {

        //given
        Song song1 = new Song();
        Song song2 = new Song();
        musicRepository.save(song1);
        musicRepository.save(song2);

        Album album = new Album();
        //앨범에 2개의 곡 저장
        album.addSongIntoAlbum(song1);
        album.addSongIntoAlbum(song2);
        albumRepository.save(album);

        //when
        albumRepository.save(album);

        //then
        Optional<Album> findAlbum = albumRepository.findById(album.getId());
        assertThat(findAlbum.get()).isEqualTo(album);

        //앨범에 수록된 음악은 2개
        assertThat(findAlbum.get().getSongList().size()).isEqualTo(2);
        assertThat(findAlbum.get().getSongList())
                .extracting("id")
                .contains(song1.getId(), song2.getId()); //pk 를 직접 비교
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