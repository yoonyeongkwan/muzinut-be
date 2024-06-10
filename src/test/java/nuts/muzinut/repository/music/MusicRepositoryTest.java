package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.Music;
import nuts.muzinut.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MusicRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MusicRepository musicRepository;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);
        Music music = new Music();
        music.createMusic(member);

        //when
        musicRepository.save(music);

        //then
        Optional<Music> findMusic = musicRepository.findById(music.getId());
        assertThat(findMusic.get()).isEqualTo(music);
        assertThat(findMusic.get()).isEqualTo(member.getMusicList().getFirst());
    }

    @Test
    void delete() {

        //given
        Music music = new Music();
        musicRepository.save(music);

        //when
        musicRepository.delete(music);

        //then
        Optional<Music> findMusic = musicRepository.findById(music.getId());
        assertThat(findMusic.isEmpty()).isTrue();

    }
}