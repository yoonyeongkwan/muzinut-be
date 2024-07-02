package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BookmarkRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired FreeBoardRepository freeBoardRepository;
    @Autowired LoungeRepository loungeRepository;
    @Autowired RecruitBoardRepository recruitBoardRepository;
    @Autowired BookmarkRepository bookmarkRepository;

    @Test
    void bookmarkFreeBoard() {

        //given
        User user = new User();
        userRepository.save(user);

        FreeBoard freeBoard = new FreeBoard();
        freeBoardRepository.save(freeBoard);

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(user, freeBoard);

        //when
        bookmarkRepository.save(bookmark);

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertThat(findBookmark.get()).isEqualTo(bookmark);
        assertThat(findBookmark.get().getUser()).isEqualTo(user);
    }

    @Test
    void bookmarkRecruitBoard() {

        //given
        User user = new User();
        userRepository.save(user);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(user, recruitBoard);

        //when
        bookmarkRepository.save(bookmark);

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertThat(findBookmark.get()).isEqualTo(bookmark);
        assertThat(findBookmark.get().getUser()).isEqualTo(user);
    }

    @Test
    void bookmarkLounge() {

        //given
        User user = new User();
        userRepository.save(user);

        Lounge lounge = new Lounge();
        loungeRepository.save(lounge);

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(user, lounge);

        //when
        bookmarkRepository.save(bookmark);

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertThat(findBookmark.get()).isEqualTo(bookmark);
        assertThat(findBookmark.get().getUser()).isEqualTo(user);
    }

    @Test
    void delete() {

        //given
        Bookmark bookmark = new Bookmark();
        bookmarkRepository.save(bookmark);

        //when
        bookmarkRepository.delete(bookmark);

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertThat(findBookmark.isEmpty()).isTrue();
    }

    //회원이 삭제되면 북마크 내용도 삭제된다
    @Test
    void deleteMember() {

        //given
        User user = new User();
        userRepository.save(user);

        Lounge lounge = new Lounge();
        loungeRepository.save(lounge);

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(user, lounge);
        bookmarkRepository.save(bookmark);

        //when
        userRepository.delete(user);

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertThat(findBookmark.isEmpty()).isTrue();
    }

    @Test
    void existsByUserAndBoard() {

        //given
        User user = new User();
        userRepository.save(user);

        FreeBoard freeBoard = new FreeBoard();
        freeBoardRepository.save(freeBoard);

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(user, freeBoard);
        bookmarkRepository.save(bookmark);

        //when
        boolean isBookmark = bookmarkRepository.existsByUserAndBoard(user, freeBoard);

        //then
        assertThat(isBookmark).isTrue();
    }

    @Test
    void deleteByUserAndBoard() {

        //given
        User user = new User();
        userRepository.save(user);

        FreeBoard freeBoard = new FreeBoard();
        freeBoardRepository.save(freeBoard);

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(user, freeBoard);
        bookmarkRepository.save(bookmark);

        //when
        bookmarkRepository.deleteByUserAndBoard(user, freeBoard.getId());

        //then
        boolean isBookmark = bookmarkRepository.existsByUserAndBoard(user, freeBoard);
        assertThat(isBookmark).isFalse();
    }
}