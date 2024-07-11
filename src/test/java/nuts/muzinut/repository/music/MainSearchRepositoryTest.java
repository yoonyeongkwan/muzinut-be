package nuts.muzinut.repository.music;

import jakarta.persistence.EntityManager;
import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Album;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.dto.mainpage.SearchArtistDto;
import nuts.muzinut.dto.mainpage.SearchSongDto;
import nuts.muzinut.repository.mainpage.MainSearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MainSearchRepositoryTest {

    @Autowired
    private MainSearchRepository mainSearchRepository;
    @Autowired
    private EntityManager em;
    @Test
    void 통합검색() {
        User user1 = new User();
        user1.setNickname("아이유");
        user1.setProfileImgFilename("img.png");
        em.persist(user1);
        User user2 = new User();
        user2.setNickname("신용재");
        user2.setProfileImgFilename("img.png");
        em.persist(user2);
        User user3 = new User();
        user3.setNickname("아아이유");
        user3.setProfileImgFilename("img.png");
        em.persist(user3);
        User user4 = new User();
        user4.setNickname("아아");
        user4.setProfileImgFilename("img.png");
        em.persist(user4);
        User user5 = new User();
        user5.setNickname("재용");
        user5.setProfileImgFilename("img.png");
        em.persist(user5);
        User user6 = new User();
        user6.setNickname("신용용용");
        user6.setProfileImgFilename("img.png");
        em.persist(user6);
        User user7 = new User();
        user7.setNickname("용용");
        user7.setProfileImgFilename("img.png");
        em.persist(user7);
        User user8 = new User();
        user8.setNickname("신재이");
        user8.setProfileImgFilename("img.png");
        em.persist(user8);
        User user9 = new User();
        user9.setNickname("버즈");
        user9.setProfileImgFilename("img.png");
        em.persist(user9);
        User user10 = new User();
        user10.setNickname("버버즈");
        user10.setProfileImgFilename("img.png");
        em.persist(user10);
        User user11 = new User();
        user11.setNickname("브로");
        user11.setProfileImgFilename("img.png");
        em.persist(user11);
        User user12 = new User();
        user12.setNickname("허각");
        user12.setProfileImgFilename("img.png");
        em.persist(user12);
        User user13 = new User();
        user13.setNickname("존박");
        user13.setProfileImgFilename("img.png");
        em.persist(user13);
        User user14 = new User();
        user14.setNickname("허걱");
        user14.setProfileImgFilename("img.png");
        em.persist(user14);
        User user15 = new User();
        user15.setNickname("허zkr");
        user15.setProfileImgFilename("img.png");
        em.persist(user15);
        User user16 = new User();
        user16.setNickname("허각악");
        user16.setProfileImgFilename("img.png");
        em.persist(user16);
        User user17 = new User();
        user17.setNickname("허름한각");
        user17.setProfileImgFilename("img.png");
        em.persist(user17);
        User user18 = new User();
        user18.setNickname("유");
        user18.setProfileImgFilename("img.png");
        em.persist(user18);
        User user19 = new User();
        user19.setNickname("바이브");
        user19.setProfileImgFilename("img.png");
        em.persist(user19);
        User user20 = new User();
        user20.setNickname("대이브");
        user20.setProfileImgFilename("img.png");
        em.persist(user20);
        User user21 = new User();
        user21.setNickname("바빠이브");
        user21.setProfileImgFilename("img.png");
        em.persist(user21);
        User user22 = new User();
        user22.setNickname("노을");
        user22.setProfileImgFilename("img.png");
        em.persist(user22);
        User user23 = new User();
        user23.setNickname("놀을");
        user23.setProfileImgFilename("img.png");
        em.persist(user23);
        User user24 = new User();
        user24.setNickname("노을이지면");
        user24.setProfileImgFilename("img.png");
        em.persist(user24);
        User user25 = new User();
        user25.setNickname("노이을");
        user25.setProfileImgFilename("img.png");
        em.persist(user25);

        Follow follow1 = new Follow();
        follow1.setUser(user1);
        follow1.setFollowingMemberId(4L);
        em.persist(follow1);
        Follow follow2 = new Follow();
        follow2.setUser(user1);
        follow2.setFollowingMemberId(4L);
        em.persist(follow2);
        Follow follow3 = new Follow();
        follow3.setUser(user2);
        follow3.setFollowingMemberId(6L);
        em.persist(follow3);
        Follow follow4 = new Follow();
        follow4.setUser(user2);
        follow4.setFollowingMemberId(6L);
        em.persist(follow4);


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
        for(int i=0; i <= 150; i++){
            Song song = new Song();

            if(i <= 50){
                song.setTitle("좋은날" + i);
                song.setAlbum(album1);
                song.setUser(user1);
            } else if (i <= 100) {
                song.setTitle("아이가" + i);
                song.setAlbum(album2);
                song.setUser(user2);
            } else {
                song.setTitle("안녕" + i);
                song.setAlbum(album3);
                song.setUser(user3);
            }
            em.persist(song);
        }

        em.flush();
        em.clear();
        String searchWord = "아이";
        System.out.println("===========아티스트 메소드 실행전===============");
        PageRequest pageRequest1 = PageRequest.of(0,10);
        Page<SearchArtistDto> searchArtistDtos = mainSearchRepository.artistSearch(searchWord, pageRequest1);
        System.out.println("===========아티스트 메소드 실행후===============");
        for (SearchArtistDto searchArtistDto : searchArtistDtos) {
            System.out.println("searchArtistDto = " + searchArtistDto);
        }

        PageRequest pageRequest2 = PageRequest.of(0,20);
        System.out.println("===========음악 메소드 실행전===============");
        Page<SearchSongDto> searchSongDtos = mainSearchRepository.songSearch(searchWord, pageRequest2);
        System.out.println("===========음악 메소드 실행후===============");
        for (SearchSongDto searchSongDto : searchSongDtos) {
            System.out.println("searchSongDto = " + searchSongDto);
        }
    }
}