package nuts.muzinut.repository.music;

import jakarta.persistence.EntityManager;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.*;
import nuts.muzinut.dto.music.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumRepositoryTest {

    @Autowired
    SongRepository musicRepository;
    @Autowired AlbumRepository albumRepository;
    @Autowired
    EntityManager em;
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

    @Test
    void 앨범상세페이지(){

        //given
        User user1 = new User();
        user1.setNickname("아이유");
        em.persist(user1);
        User user2 = new User();
        user2.setNickname("신용재");
        em.persist(user2);
        User user3 = new User();
        user3.setNickname("허각");
        em.persist(user3);
        Album album1 = new Album();
        album1.setAlbumImg("aaa");
        album1.setUser(user1);
        em.persist(album1);
        Album album2 = new Album();
        album2.setAlbumImg("bbb");
        album2.setUser(user2);
        em.persist(album2);
        Album album3 = new Album();
        album3.setAlbumImg("ccc");
        album3.setUser(user3);
        em.persist(album3);
        for(int i=1; i <= 200; i++){
            Song song = new Song();
            song.setTitle("제목" + i);
            if(i <= 50){
                song.setAlbum(album1);
                song.setUser(user1);
                song.setLyrics("가사입니다");
                song.setLyricist("아이유");
                song.setComposer("아이유");
            } else if (i <= 100) {
                song.setAlbum(album2);
                song.setUser(user2);
                song.setLyrics("가사입니다");
                song.setLyricist("아이유");
                song.setComposer("아이유");
            } else {
                song.setAlbum(album3);
                song.setUser(user3);
                song.setLyrics("가사입니다");
                song.setLyricist("아이유");
                song.setComposer("아이유");
            }
            em.persist(song);

            // Genre( KPOP, BALLAD, POP, HIPHOP, RNB, INDIE, TROT, VIRTUBER, ETC )
            if(i <= 30){
                SongGenre songGenre = new SongGenre(Genre.KPOP);
                songGenre.setSong(song);
                em.persist(songGenre);
                SongGenre songGenre2 = new SongGenre(Genre.HIPHOP);
                songGenre2.setSong(song);
                em.persist(songGenre2);
            } else if (i <= 80) {
                SongGenre songGenre = new SongGenre(Genre.BALLAD);
                songGenre.setSong(song);
                em.persist(songGenre);
                SongGenre songGenre2 = new SongGenre(Genre.RNB);
                songGenre2.setSong(song);
                em.persist(songGenre2);
            } else if (i <= 130) {
                SongGenre songGenre = new SongGenre(Genre.VIRTUBER);
                songGenre.setSong(song);
                em.persist(songGenre);
                SongGenre songGenre2 = new SongGenre(Genre.INDIE);
                songGenre2.setSong(song);
                em.persist(songGenre2);
            } else if (i <= 160) {
                SongGenre songGenre = new SongGenre(Genre.ETC);
                songGenre.setSong(song);
                em.persist(songGenre);
                SongGenre songGenre2 = new SongGenre(Genre.TROT);
                songGenre2.setSong(song);
                em.persist(songGenre2);
            } else if (i <= 180) {
                SongGenre songGenre = new SongGenre(Genre.POP);
                songGenre.setSong(song);
                em.persist(songGenre);
                SongGenre songGenre2 = new SongGenre(Genre.RNB);
                songGenre2.setSong(song);
                em.persist(songGenre2);
            } else {
                SongGenre songGenre = new SongGenre(Genre.BALLAD);
                songGenre.setSong(song);
                em.persist(songGenre);
                SongGenre songGenre2 = new SongGenre(Genre.TROT);
                songGenre2.setSong(song);
                em.persist(songGenre2);
            }
            if (i <= 200) {
                for (int j = 0; j < i; j++) {
                    SongLike songLike = new SongLike(song);
                    em.persist(songLike);
                }
            }

        }
        em.flush();
        em.clear();


        List<AlbumDetaillDto> albumDetaillDtos = albumRepository.albumDetaill(1L);
        List<AlbumSongDetaillDto> albumSongDetaillDtos = albumRepository.albumSongDetaill(1L);
        for (AlbumDetaillDto albumDetaillDto : albumDetaillDtos) {
            System.out.println("albumDetaillDto = " + albumDetaillDto);
        }
        List<AlbumSongDetaillDto> songs = new ArrayList<>();
        for (AlbumSongDetaillDto albumSongDetaillDto : albumSongDetaillDtos) {
            System.out.println("albumSongDetaillDto = " + albumSongDetaillDto);
            songs.add(albumSongDetaillDto);
        }
        AlbumDetaillDto albumDetaillDto = albumDetaillDtos.get(0);
        AlbumDetaillResultDto albumDetaillResultDto = new AlbumDetaillResultDto(
                albumDetaillDto.getName(), albumDetaillDto.getAlbumImg(),
                albumDetaillDto.getNickname(), albumDetaillDto.getIntro(),
                songs
        );
        System.out.println("albumDetaillResultDto = " + albumDetaillResultDto);
    }
}