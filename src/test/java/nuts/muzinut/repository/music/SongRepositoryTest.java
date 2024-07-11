//package nuts.muzinut.repository.music;
//
//import com.querydsl.core.Tuple;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import nuts.muzinut.domain.member.User;
//import nuts.muzinut.domain.music.*;
//import nuts.muzinut.dto.music.SongDetailDto;
//import nuts.muzinut.dto.music.SongDetaillResultDto;
//import nuts.muzinut.dto.music.SongGenreDto;
//import nuts.muzinut.dto.music.SongPageDto;
//import nuts.muzinut.repository.mainpage.MainSearchRepository;
//import nuts.muzinut.repository.member.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class SongRepositoryTest {
//
//    @Autowired
//    MainSearchRepository mainSearchRepository;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    SongRepository musicRepository;
//    @Autowired
//    EntityManager em;
//    @Autowired
//    SongGenreRepository songGenreRepository;
//    @Autowired
//    JPAQueryFactory queryFactory;
//    @Test
//    void save() {
//
//        //given
//        User user = new User();
//        userRepository.save(user);
//        Song song = new Song();
//        song.createMusic(user);
//
//        //when
//        musicRepository.save(song);
//
//        //then
//        Optional<Song> findMusic = musicRepository.findById(song.getId());
//        assertThat(findMusic.get()).isEqualTo(song);
//        assertThat(findMusic.get()).isEqualTo(user.getSongList().getFirst());
//    }
//
//    @Test
//    void delete() {
//
//        //given
//        Song song = new Song();
//        musicRepository.save(song);
//
//        //when
//        musicRepository.delete(song);
//
//        //then
//        Optional<Song> findMusic = musicRepository.findById(song.getId());
//        assertThat(findMusic.isEmpty()).isTrue();
//
//    }
//
//    @Test
//    void 최신음악() {
//
//        //given
//        User user1 = new User();
//        user1.setNickname("아이유");
//        em.persist(user1);
//        User user2 = new User();
//        user2.setNickname("신용재");
//        em.persist(user2);
//        User user3 = new User();
//        user3.setNickname("허각");
//        em.persist(user3);
//        Album album1 = new Album();
//        album1.setAlbumImg("aaa");
//        album1.setUser(user1);
//        em.persist(album1);
//        Album album2 = new Album();
//        album2.setAlbumImg("bbb");
//        album2.setUser(user2);
//        em.persist(album2);
//        Album album3 = new Album();
//        album3.setAlbumImg("ccc");
//        album3.setUser(user3);
//        em.persist(album3);
//        for(int i=0; i <= 150; i++){
//            Song song = new Song();
//            song.setTitle("제목" + i);
//            if(i <= 50){
//                song.setAlbum(album1);
//                song.setUser(user1);
//            } else if (i <= 100) {
//                song.setAlbum(album2);
//                song.setUser(user2);
//            } else {
//                song.setAlbum(album3);
//                song.setUser(user3);
//            }
//            em.persist(song);
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt(); // 현재 스레드에 인터럽트 상태를 복원합니다.
//                throw new RuntimeException(e);
//            }
//        }
//
//        PageRequest pageRequest = PageRequest.of(2, 20);
//        Page<SongPageDto> newSongDtos = musicRepository.new100Song(pageRequest);
//        List<SongPageDto> songPageDtosList = newSongDtos.toList();
//        for (SongPageDto songPageDto : newSongDtos) {
//            System.out.println("songPageDto = " + songPageDto);
//        }
//    }
//    @Test
//    void 인기TOP100() {
//
//        //given
//        User user1 = new User();
//        user1.setNickname("아이유");
//        em.persist(user1);
//        User user2 = new User();
//        user2.setNickname("신용재");
//        em.persist(user2);
//        User user3 = new User();
//        user3.setNickname("허각");
//        em.persist(user3);
//        Album album1 = new Album();
//        album1.setAlbumImg("aaa");
//        album1.setUser(user1);
//        em.persist(album1);
//        Album album2 = new Album();
//        album2.setAlbumImg("bbb");
//        album2.setUser(user2);
//        em.persist(album2);
//        Album album3 = new Album();
//        album3.setAlbumImg("ccc");
//        album3.setUser(user3);
//        em.persist(album3);
//        for(int i=1; i <= 150; i++){
//            Song song = new Song();
//            song.setTitle("제목" + i);
//            if(i <= 50){
//                song.setAlbum(album1);
//                song.setUser(user1);
//            } else if (i <= 100) {
//                song.setAlbum(album2);
//                song.setUser(user2);
//            } else {
//                song.setAlbum(album3);
//                song.setUser(user3);
//            }
//            em.persist(song);
//            if (i <= 100) {
//                for (int j = 0; j < i; j++) {
//                    PlayView playView = new PlayView(song);
//                    em.persist(playView);
//                }
//            }
//
//        }
//        em.flush();
//        em.clear();
//
//        PageRequest pageRequest = PageRequest.of(0, 20);
//        Page<SongPageDto> newSongDtos = musicRepository.hotTOP100Song(pageRequest);
//        for (SongPageDto newSongDto : newSongDtos) {
//            System.out.println("newSongDto = " + newSongDto);
//        }
//    }
//
//    @Test
//    void 장르별음악(){
//
//        //given
//        User user1 = new User();
//        user1.setNickname("아이유");
//        em.persist(user1);
//        User user2 = new User();
//        user2.setNickname("신용재");
//        em.persist(user2);
//        User user3 = new User();
//        user3.setNickname("허각");
//        em.persist(user3);
//        Album album1 = new Album();
//        album1.setAlbumImg("aaa");
//        album1.setUser(user1);
//        em.persist(album1);
//        Album album2 = new Album();
//        album2.setAlbumImg("bbb");
//        album2.setUser(user2);
//        em.persist(album2);
//        Album album3 = new Album();
//        album3.setAlbumImg("ccc");
//        album3.setUser(user3);
//        em.persist(album3);
//        for(int i=1; i <= 200; i++){
//            Song song = new Song();
//            song.setTitle("제목" + i);
//            if(i <= 50){
//                song.setAlbum(album1);
//                song.setUser(user1);
//            } else if (i <= 100) {
//                song.setAlbum(album2);
//                song.setUser(user2);
//            } else {
//                song.setAlbum(album3);
//                song.setUser(user3);
//            }
//            em.persist(song);
//            // Genre( KPOP, BALLAD, POP, HIPHOP, RNB, INDIE, TROT, VIRTUBER, ETC )
//            if(i <= 30){
//                SongGenre songGenre = new SongGenre(Genre.KPOP);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.HIPHOP);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            } else if (i <= 80) {
//                SongGenre songGenre = new SongGenre(Genre.BALLAD);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.RNB);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            } else if (i <= 130) {
//                SongGenre songGenre = new SongGenre(Genre.VIRTUBER);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.INDIE);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            } else if (i <= 160) {
//                SongGenre songGenre = new SongGenre(Genre.ETC);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.TROT);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            } else if (i <= 180) {
//                SongGenre songGenre = new SongGenre(Genre.POP);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.RNB);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            } else {
//                SongGenre songGenre = new SongGenre(Genre.BALLAD);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.TROT);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            }
//            if (i <= 200) {
//                for (int j = 0; j < i; j++) {
//                    PlayView playView = new PlayView(song);
//                    em.persist(playView);
//                }
//            }
//
//        }
//        em.flush();
//        em.clear();
//
//
//        PageRequest pageRequest = PageRequest.of(0, 20);
//        Page<SongPageDto> page = musicRepository.genreSong("HIPHOP", pageRequest);
////        PageDto<SongPageDto> results = new PageDto<>(
////                page.getContent(),
////                page.getNumber(),
////                page.getSize(),
////                page.getTotalElements()
////        );
////
////        System.out.println(results.getTotalElements());
////        System.out.println(results.getTotalPages());
//    }
//    @Test
//    void 음악상세페이지(){
//
//        //given
//        User user1 = new User();
//        user1.setNickname("아이유");
//        em.persist(user1);
//        User user2 = new User();
//        user2.setNickname("신용재");
//        em.persist(user2);
//        User user3 = new User();
//        user3.setNickname("허각");
//        em.persist(user3);
//        Album album1 = new Album();
//        album1.setAlbumImg("aaa");
//        album1.setUser(user1);
//        em.persist(album1);
//        Album album2 = new Album();
//        album2.setAlbumImg("bbb");
//        album2.setUser(user2);
//        em.persist(album2);
//        Album album3 = new Album();
//        album3.setAlbumImg("ccc");
//        album3.setUser(user3);
//        em.persist(album3);
//        for(int i=1; i <= 200; i++){
//            Song song = new Song();
//            song.setTitle("제목" + i);
//            if(i <= 50){
//                song.setAlbum(album1);
//                song.setUser(user1);
//                song.setLyrics("가사입니다");
//                song.setLyricist("아이유");
//                song.setComposer("아이유");
//            } else if (i <= 100) {
//                song.setAlbum(album2);
//                song.setUser(user2);
//                song.setLyrics("가사입니다");
//                song.setLyricist("아이유");
//                song.setComposer("아이유");
//            } else {
//                song.setAlbum(album3);
//                song.setUser(user3);
//                song.setLyrics("가사입니다");
//                song.setLyricist("아이유");
//                song.setComposer("아이유");
//            }
//            em.persist(song);
//
//            // Genre( KPOP, BALLAD, POP, HIPHOP, RNB, INDIE, TROT, VIRTUBER, ETC )
//            if(i <= 30){
//                SongGenre songGenre = new SongGenre(Genre.KPOP);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.HIPHOP);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            } else if (i <= 80) {
//                SongGenre songGenre = new SongGenre(Genre.BALLAD);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.RNB);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            } else if (i <= 130) {
//                SongGenre songGenre = new SongGenre(Genre.VIRTUBER);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.INDIE);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            } else if (i <= 160) {
//                SongGenre songGenre = new SongGenre(Genre.ETC);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.TROT);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            } else if (i <= 180) {
//                SongGenre songGenre = new SongGenre(Genre.POP);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.RNB);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            } else {
//                SongGenre songGenre = new SongGenre(Genre.BALLAD);
//                songGenre.setSong(song);
//                em.persist(songGenre);
//                SongGenre songGenre2 = new SongGenre(Genre.TROT);
//                songGenre2.setSong(song);
//                em.persist(songGenre2);
//            }
//            if (i <= 200) {
//                for (int j = 0; j < i; j++) {
//                    SongLike songLike = new SongLike(song);
//                    em.persist(songLike);
//                }
//            }
//
//        }
//        em.flush();
//        em.clear();
//
//
//        List<SongDetailDto> songDetaillDtos = musicRepository.songDetaill(10L);
//        List<SongGenreDto> songGenreDtos = musicRepository.songDetaillGenre(10L);
//        for (SongDetailDto songDetaillDto : songDetaillDtos) {
//            System.out.println("songDetaillDto = " + songDetaillDto);
//
//        }
//        List<Genre> genres = new ArrayList<>();
//        for (SongGenreDto songGenreDto : songGenreDtos) {
//            System.out.println("songGenreDto = " + songGenreDto);
//            genres.add(songGenreDto.getGenre());
//        }
//        SongDetailDto result = songDetaillDtos.get(0);
//        SongDetaillResultDto songDetaillResultDto = new SongDetaillResultDto(
//                result.getAlbumImg(), result.getTitle(),
//                result.getNickname(), result.getLikeCount(),
//                result.getLyrics(), result.getComposer(),
//                result.getLyricist(), result.getAlbumId(),
//                genres
//        );
//        System.out.println("songDetaillResultDto = " + songDetaillResultDto);
//    }
//
//}