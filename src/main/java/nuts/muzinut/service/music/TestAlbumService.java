package nuts.muzinut.service.music;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.*;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.AlbumRepository;
import nuts.muzinut.repository.music.PlayNutMusicRepository;
import nuts.muzinut.repository.music.PlayNutRepository;
import nuts.muzinut.repository.music.SongRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestAlbumService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final PlayNutRepository playNutRepository;
    private final PlayNutMusicRepository playNutMusicRepository;

    @PersistenceContext
    EntityManager em;

    @Value("${spring.file.dir}")
    private String fileDir;

    public void albumDataInit() {
        Optional<User> optional1 = userRepository.findById(1L);
        Optional<User> optional2 = userRepository.findById(2L);
        Optional<User> optional3 = userRepository.findById(3L);
        User user1 = optional1.get();
        User user2 = optional1.get();
        User user3 = optional1.get();
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
            } else if (i <= 100) {
                song.setAlbum(album2);
                song.setUser(user2);
            } else {
                song.setAlbum(album3);
                song.setUser(user3);
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
                    PlayView playView = new PlayView(song);
                    em.persist(playView);
                }
            }

        }


//        PlayNut playNut = new PlayNut("케이팝", user1);
//        playNutRepository.save(playNut);
//
//        for (int i=101; i <= 200; i++){
//
//            long id = i;
//            PlayNutMusic playNutMusic = new PlayNutMusic(playNut, id);
//            playNutMusicRepository.save(playNutMusic);
//        }
    }
}
