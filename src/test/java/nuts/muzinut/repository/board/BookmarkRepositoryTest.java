package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.Member;
import nuts.muzinut.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BookmarkRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired FreeBoardRepository freeBoardRepository;
    @Autowired LoungeRepository loungeRepository;
    @Autowired RecruitBoardRepository recruitBoardRepository;
    @Autowired BookmarkRepository bookmarkRepository;

    @Test
    void bookmarkFreeBoard() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        FreeBoard freeBoard = new FreeBoard();
        freeBoardRepository.save(freeBoard);

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(member, freeBoard.getId(), BoardType.FREE);

        //when
        bookmarkRepository.save(bookmark);

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertThat(findBookmark.get()).isEqualTo(bookmark);
        assertThat(findBookmark.get().getMember()).isEqualTo(member);
        assertThat(findBookmark.get().getBoardId()).isEqualTo(freeBoard.getId());
        assertThat(findBookmark.get().getBoardType()).isEqualTo(BoardType.FREE);
    }

    @Test
    void bookmarkRecruitBoard() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(member, recruitBoard.getId(), BoardType.RECRUIT);

        //when
        bookmarkRepository.save(bookmark);

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertThat(findBookmark.get()).isEqualTo(bookmark);
        assertThat(findBookmark.get().getMember()).isEqualTo(member);
        assertThat(findBookmark.get().getBoardId()).isEqualTo(recruitBoard.getId());
        assertThat(findBookmark.get().getBoardType()).isEqualTo(BoardType.RECRUIT);
    }

    @Test
    void bookmarkLounge() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Lounge lounge = new Lounge();
        loungeRepository.save(lounge);

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(member, lounge.getId(), BoardType.LOUNGE);

        //when
        bookmarkRepository.save(bookmark);

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertThat(findBookmark.get()).isEqualTo(bookmark);
        assertThat(findBookmark.get().getMember()).isEqualTo(member);
        assertThat(findBookmark.get().getBoardId()).isEqualTo(lounge.getId());
        assertThat(findBookmark.get().getBoardType()).isEqualTo(BoardType.LOUNGE);
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
        Member member = new Member();
        memberRepository.save(member);

        Lounge lounge = new Lounge();
        loungeRepository.save(lounge);

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(member, lounge.getId(), BoardType.LOUNGE);
        bookmarkRepository.save(bookmark);

        //when
        memberRepository.delete(member);

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertThat(findBookmark.isEmpty()).isTrue();
    }
}