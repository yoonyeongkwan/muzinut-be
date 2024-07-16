package nuts.muzinut.service.music;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.*;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.PlayNutMusicRepository;
import nuts.muzinut.repository.music.PlayNutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumDataInit {
    private final UserRepository userRepository;
    private final PlayNutRepository playNutRepository;
    private final PlayNutMusicRepository playNutMusicRepository;
    private final EntityManager em;

    public void albumData() {
        Optional<User> optional1 = userRepository.findById(1L);
        Optional<User> optional2 = userRepository.findById(2L);
        Optional<User> optional3 = userRepository.findById(3L);
        User user1 = optional1.get();
        User user2 = optional2.get();
        User user3 = optional3.get();
        Album album1 = new Album(user1,"앨범1","앨범1입니다","aaa.png");
        em.persist(album1);
        Album album2 = new Album(user2,"앨범2","앨범2입니다","bbb.png");
        em.persist(album2);
        Album album3 = new Album(user3,"앨범3","앨범3입니다","ccc.png");
        em.persist(album3);

        for(int i=1; i <= 200; i++){
            if(i <= 50){
                Song song = new Song(user1,"제목"+i,"가사입니다","아이유","아이유","song1.mp3",album1);
                em.persist(song);
                SongGenre songGenre = new SongGenre(Genre.KPOP);
                songGenre.addMusicGenre(song);
                em.persist(songGenre);
                SongGenre songGenre2 = new SongGenre(Genre.HIPHOP);
                songGenre2.addMusicGenre(song);
                em.persist(songGenre2);
                for (int j = 0; j < i; j++) {
                    PlayView playView = new PlayView(song);
                    em.persist(playView);
                    SongLike songLike = new SongLike(user1,song);
                    em.persist(songLike);
                }
            } else if (i <= 100) {
                Song song = new Song(user2,"제목"+i,"가사입니다","아이유","아이유","song1.mp3",album2);
                em.persist(song);
                SongGenre songGenre = new SongGenre(Genre.BALLAD);
                songGenre.addMusicGenre(song);
                em.persist(songGenre);
                SongGenre songGenre2 = new SongGenre(Genre.RNB);
                songGenre2.addMusicGenre(song);
                em.persist(songGenre2);
                for (int j = 0; j < i; j++) {
                    PlayView playView = new PlayView(song);
                    em.persist(playView);
                    SongLike songLike = new SongLike(user1,song);
                    em.persist(songLike);
                }
            } else {
                Song song = new Song(user3,"제목"+i,"가사입니다","아이유","아이유","song1.mp3",album3);
                em.persist(song);
                SongGenre songGenre = new SongGenre(Genre.VIRTUBER);
                songGenre.addMusicGenre(song);
                em.persist(songGenre);
                SongGenre songGenre2 = new SongGenre(Genre.INDIE);
                songGenre2.addMusicGenre(song);
                em.persist(songGenre2);
                for (int j = 0; j < i; j++) {
                    PlayView playView = new PlayView(song);
                    em.persist(playView);
                    SongLike songLike = new SongLike(user1,song);
                    em.persist(songLike);
                }
            }
            // Genre( KPOP, BALLAD, POP, HIPHOP, RNB, INDIE, TROT, VIRTUBER, ETC )

        }

        PlayNut playNut = new PlayNut("케이팝", user1);
        playNutRepository.save(playNut);

        for (int i=41; i <= 60; i++){

            long id = i;
            PlayNutMusic playNutMusic = new PlayNutMusic(playNut, id);
            playNutMusicRepository.save(playNutMusic);
        }
    }
}
