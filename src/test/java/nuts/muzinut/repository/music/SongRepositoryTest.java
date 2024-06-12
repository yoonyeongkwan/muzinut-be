package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class SongRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MusicRepository musicRepository;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);
        Song song = new Song();
        song.createMusic(member);

        //when
        musicRepository.save(song);

        //then
        Optional<Song> findMusic = musicRepository.findById(song.getId());
        assertThat(findMusic.get()).isEqualTo(song);
        assertThat(findMusic.get()).isEqualTo(member.getSongList().getFirst());
    }

    @Test
    void delete() {

        //given
        Song song = new Song();
        musicRepository.save(song);

        //when
        musicRepository.delete(song);

        //then
        Optional<Song> findMusic = musicRepository.findById(song.getId());
        assertThat(findMusic.isEmpty()).isTrue();

    }
}