package nuts.muzinut.repository.board;

import jakarta.transaction.Transactional;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.MusicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class LikeRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired FreeBoardRepository freeBoardRepository;
    @Autowired LoungeRepository loungeRepository;
    @Autowired RecruitBoardRepository recruitBoardRepository;
    @Autowired LikeRepository likeRepository;

    @Test
    void loungeLike() {

        //given
        User user = new User();
        userRepository.save(user);

        Lounge lounge = new Lounge();
        loungeRepository.save(lounge);

        Like like = new Like();
        like.addLike(user, lounge);

        //when
        likeRepository.save(like);

        //then
        Optional<Like> findLike = likeRepository.findById(like.getId());
        assertThat(findLike.get()).isEqualTo(like);
        assertThat(findLike.get().getUser()).isEqualTo(user);
        assertThat(findLike.get().getBoard()).isEqualTo(lounge);
    }

    @Test
    void freeBoardLike() {

        //given
        User user = new User();
        userRepository.save(user);

        FreeBoard freeBoard = new FreeBoard();
        freeBoardRepository.save(freeBoard);

        Like like = new Like();
        like.addLike(user, freeBoard);


        //when
        likeRepository.save(like);

        //then
        Optional<Like> findLike = likeRepository.findById(like.getId());
        assertThat(findLike.get()).isEqualTo(like);
        assertThat(findLike.get().getUser()).isEqualTo(user);
        assertThat(findLike.get().getBoard()).isEqualTo(freeBoard);
    }

    @Test
    void RecruitBoardLike() {

        //given
        User user = new User();
        userRepository.save(user);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        Like like = new Like();
        like.addLike(user, recruitBoard);

        //when
        likeRepository.save(like);

        //then
        Optional<Like> findLike = likeRepository.findById(like.getId());
        assertThat(findLike.get()).isEqualTo(like);
        assertThat(findLike.get().getUser()).isEqualTo(user);
        assertThat(findLike.get().getBoard()).isEqualTo(recruitBoard);
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