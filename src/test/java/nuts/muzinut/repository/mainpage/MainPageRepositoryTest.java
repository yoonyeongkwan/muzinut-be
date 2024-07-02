package nuts.muzinut.repository.mainpage;

import com.querydsl.core.Tuple;
import jakarta.persistence.EntityManager;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.*;
import nuts.muzinut.dto.mainpage.*;
import nuts.muzinut.repository.board.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QFreeBoard.freeBoard;
import static nuts.muzinut.domain.board.QRecruitBoard.recruitBoard;
import static nuts.muzinut.domain.member.QUser.user;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MainPageRepositoryTest {


    @Autowired
    EntityManager em;
    @Autowired
    MainPageRepository mainPageRepository;
    @Autowired
    BoardRepository boardRepository;


    @Test
    void 메인페이지음악() {
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

        Follow follow1 = new Follow();
        follow1.setUser(user1);
        follow1.setFollowingMemberId(2L);
        em.persist(follow1);
        Follow follow2 = new Follow();
        follow2.setUser(user1);
        follow2.setFollowingMemberId(3L);
        em.persist(follow2);
        Follow follow3 = new Follow();
        follow3.setUser(user2);
        follow3.setFollowingMemberId(3L);
        em.persist(follow3);
        Follow follow4 = new Follow();
        follow4.setUser(user2);
        follow4.setFollowingMemberId(4L);
        em.persist(follow4);
        Follow follow5 = new Follow();
        follow5.setUser(user2);
        follow5.setFollowingMemberId(4L);
        em.persist(follow5);
        Follow follow6 = new Follow();
        follow6.setUser(user2);
        follow6.setFollowingMemberId(4L);
        em.persist(follow6);
        Follow follow7 = new Follow();
        follow7.setUser(user2);
        follow7.setFollowingMemberId(5L);
        em.persist(follow7);
        Follow follow8 = new Follow();
        follow8.setUser(user2);
        follow8.setFollowingMemberId(5L);
        em.persist(follow8);
        Follow follow9 = new Follow();
        follow9.setUser(user2);
        follow9.setFollowingMemberId(5L);
        em.persist(follow9);
        Follow follow10 = new Follow();
        follow10.setUser(user2);
        follow10.setFollowingMemberId(5L);
        em.persist(follow10);
        Follow follow11 = new Follow();
        follow11.setUser(user2);
        follow11.setFollowingMemberId(6L);
        em.persist(follow11);
        Follow follow12 = new Follow();
        follow12.setUser(user2);
        follow12.setFollowingMemberId(6L);
        em.persist(follow12);
        Follow follow13 = new Follow();
        follow13.setUser(user2);
        follow13.setFollowingMemberId(6L);
        em.persist(follow13);
        Follow follow14 = new Follow();
        follow14.setUser(user2);
        follow14.setFollowingMemberId(6L);
        em.persist(follow14);
        Follow follow15 = new Follow();
        follow15.setUser(user2);
        follow15.setFollowingMemberId(6L);
        em.persist(follow15);

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
                    PlayView playView = new PlayView(song);
                    em.persist(playView);
                    SongLike songLike = new SongLike(song);
                    em.persist(songLike);
                }
            }

        }
        em.flush();
        em.clear();

        List<HotArtistDto> top5Artist = mainPageRepository.findTOP5Artist();
        List<NewSongDto> newSong = mainPageRepository.findNewSong();
        List<HotSongDto> top10Song = mainPageRepository.findTOP10Song();
        for (HotSongDto hotSongDto : top10Song) {
            System.out.println("hotSongDto = " + hotSongDto);
        }
        for (NewSongDto newSongDto : newSong) {
            System.out.println("newSongDto = " + newSongDto);
        }
        for (HotArtistDto hotArtistDto : top5Artist) {
            System.out.println("hotArtistDto = " + hotArtistDto);
        }
    }

    @Test
    void 메인페이지인기게시판(){
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

        for(int i=1; i<=100; i++){
            if(i <= 30){
                FreeBoard freeBoard = new FreeBoard("프리게시판 제목"+i);
                freeBoard.addBoard(user1);
                RecruitBoard recruitBoard = new RecruitBoard("모집게시판 제목"+i);
                recruitBoard.addBoard(user1);
                em.persist(freeBoard);
                em.persist(recruitBoard);
            } else if (i <= 70) {
                FreeBoard freeBoard = new FreeBoard("프리게시판 제목"+i);
                freeBoard.addBoard(user2);
                RecruitBoard recruitBoard = new RecruitBoard("모집게시판 제목"+i);
                recruitBoard.addBoard(user2);
                if (i == 35){
                    freeBoard.addView();
                } else if (i == 50) {
                    recruitBoard.incrementView();recruitBoard.incrementView();
                }
                em.persist(freeBoard);
                em.persist(recruitBoard);
            } else {
                FreeBoard freeBoard = new FreeBoard("프리게시판 제목"+i);
                freeBoard.addBoard(user3);
                RecruitBoard recruitBoard = new RecruitBoard("모집게시판 제목"+i);
                recruitBoard.addBoard(user3);
                if (i == 75) {
                    freeBoard.addView();freeBoard.addView();freeBoard.addView();
                } else if (i== 88) {
                    recruitBoard.incrementView();recruitBoard.incrementView();recruitBoard.incrementView();recruitBoard.incrementView();
                } else if (i == 97) {
                    freeBoard.addView();freeBoard.addView();freeBoard.addView();freeBoard.addView();freeBoard.addView();
                }
                em.persist(freeBoard);
                em.persist(recruitBoard);
            }

        }
        em.flush();
        em.clear();
//        List<HotBoardDto> hotBoard = mainPageRepository.findHotBoard();
//        for (HotBoardDto hotBoardDto : hotBoard) {
//            System.out.println("hotBoardDto = " + hotBoardDto);
//        }


//        List<Tuple> content = mainPageRepository.findHotBoard();
//        List<HotBoardDto> hotBoardDtos = content.stream()
//                .map(tuple -> new HotBoardDto(
//                        tuple.get(board.id),
//                        tuple.get(board.title),
//                        tuple.get(user.nickname),
//                        tuple.get(board.view)
//                ))
//                .collect(Collectors.toList());
//
//        for (HotBoardDto hotBoardDto : hotBoardDtos) {
//            System.out.println("hotBoardDto = " + hotBoardDto);
//        }
    }

    @Test
    void 메인페이지최신게판() {
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

        for(int i=1; i<=100; i++){
            if(i <= 30){
                FreeBoard freeBoard = new FreeBoard("프리게시판 제목"+i);
                freeBoard.addBoard(user1);
                RecruitBoard recruitBoard = new RecruitBoard("모집게시판 제목"+i);
                recruitBoard.addBoard(user1);
                em.persist(freeBoard);
                em.persist(recruitBoard);
            } else if (i <= 70) {
                FreeBoard freeBoard = new FreeBoard("프리게시판 제목"+i);
                freeBoard.addBoard(user2);
                RecruitBoard recruitBoard = new RecruitBoard("모집게시판 제목"+i);
                recruitBoard.addBoard(user2);
                if (i == 35){
                    freeBoard.addView();
                } else if (i == 50) {
                    recruitBoard.incrementView();recruitBoard.incrementView();
                }
                em.persist(freeBoard);
                em.persist(recruitBoard);
            } else {
                FreeBoard freeBoard = new FreeBoard("프리게시판 제목"+i);
                freeBoard.addBoard(user3);
                RecruitBoard recruitBoard = new RecruitBoard("모집게시판 제목"+i);
                recruitBoard.addBoard(user3);
                if (i == 75) {
                    freeBoard.addView();freeBoard.addView();freeBoard.addView();
                } else if (i== 88) {
                    recruitBoard.incrementView();recruitBoard.incrementView();recruitBoard.incrementView();recruitBoard.incrementView();
                } else if (i == 97) {
                    freeBoard.addView();freeBoard.addView();freeBoard.addView();freeBoard.addView();freeBoard.addView();
                }
                em.persist(freeBoard);
                em.persist(recruitBoard);
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 현재 스레드에 인터럽트 상태를 복원합니다.
                throw new RuntimeException(e);
            }
        }
        em.flush();
        em.clear();

//        List<NewBoardDto> newBoard = mainPageRepository.findNewBoard();
//
//        NewBoardDto newBoardDto = newBoard.get(0);
//        System.out.println("newBoardDto = " + newBoardDto);
    }
}