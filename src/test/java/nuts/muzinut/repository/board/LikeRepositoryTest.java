package nuts.muzinut.repository.board;

import jakarta.transaction.Transactional;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.repository.member.MemberRepository;
import nuts.muzinut.repository.music.MusicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class LikeRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired FreeBoardRepository freeBoardRepository;
    @Autowired LoungeRepository loungeRepository;
    @Autowired RecruitBoardRepository recruitBoardRepository;
    @Autowired MusicRepository musicRepository;
    @Autowired LikeRepository likeRepository;

    @Test
    void musicLike() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Song song = new Song();
        musicRepository.save(song);

        Like like = new Like(song.getId(), BoardType.MUSIC, member);

        //when
        likeRepository.save(like);

        //then
        Optional<Like> findLike = likeRepository.findById(like.getId());
        assertThat(findLike.get()).isEqualTo(like);
        assertThat(findLike.get().getMember()).isEqualTo(member);
        assertThat(findLike.get().getBoardId()).isEqualTo(song.getId());
    }

    @Test
    void loungeLike() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Lounge lounge = new Lounge();
        loungeRepository.save(lounge);

        Like like = new Like(lounge.getId(), BoardType.LOUNGE, member);

        //when
        likeRepository.save(like);

        //then
        Optional<Like> findLike = likeRepository.findById(like.getId());
        assertThat(findLike.get()).isEqualTo(like);
        assertThat(findLike.get().getMember()).isEqualTo(member);
        assertThat(findLike.get().getBoardId()).isEqualTo(lounge.getId());
    }

    @Test
    void freeBoardLike() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        FreeBoard freeBoard = new FreeBoard();
        freeBoardRepository.save(freeBoard);

        Like like = new Like(freeBoard.getId(), BoardType.LOUNGE, member);

        //when
        likeRepository.save(like);

        //then
        Optional<Like> findLike = likeRepository.findById(like.getId());
        assertThat(findLike.get()).isEqualTo(like);
        assertThat(findLike.get().getMember()).isEqualTo(member);
        assertThat(findLike.get().getBoardId()).isEqualTo(freeBoard.getId());
    }

    @Test
    void RecruitBoardLike() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        Like like = new Like(recruitBoard.getId(), BoardType.LOUNGE, member);

        //when
        likeRepository.save(like);

        //then
        Optional<Like> findLike = likeRepository.findById(like.getId());
        assertThat(findLike.get()).isEqualTo(like);
        assertThat(findLike.get().getMember()).isEqualTo(member);
        assertThat(findLike.get().getBoardId()).isEqualTo(recruitBoard.getId());
    }

    @Test
    void delete() {

        //given
        Like like = new Like();
        likeRepository.save(like);

        //when
        likeRepository.delete(like);

        //then
        Optional<Like> findLike = likeRepository.findById(like.getId());
        assertThat(findLike.isEmpty()).isTrue();
    }
}