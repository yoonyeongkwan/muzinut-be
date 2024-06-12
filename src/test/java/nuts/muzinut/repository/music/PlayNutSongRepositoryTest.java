package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.domain.music.PlayNut;
import nuts.muzinut.domain.music.PlayNutMusic;
import nuts.muzinut.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PlayNutSongRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PleNutRepository pleNutRepository;
    @Autowired
    MusicRepository musicRepository;
    @Autowired
    PleNutMusicRepository pleNutMusicRepository;

    @Test
    void save() {

        //given
        PlayNut playNut = new PlayNut();
        pleNutRepository.save(playNut);

        Song song = new Song();
        musicRepository.save(song);

        PlayNutMusic playNutMusic = new PlayNutMusic();
        playNutMusic.addPlaylistMusic(playNut, song);

        //when
        pleNutMusicRepository.save(playNutMusic);

        //then
        Optional<PlayNutMusic> findPleNutMusic = pleNutMusicRepository.findById(playNutMusic.getId());
        assertThat(findPleNutMusic.get()).isEqualTo(playNutMusic);
        assertThat(findPleNutMusic.get().getPlayNut()).isEqualTo(playNut);
    }

    @Test
    void delete() {

        //given
        PlayNutMusic playNutMusic = new PlayNutMusic();
        pleNutMusicRepository.save(playNutMusic);

        //when
        pleNutMusicRepository.delete(playNutMusic);

        //then
        Optional<PlayNutMusic> findPleNutMusic = pleNutMusicRepository.findById(playNutMusic.getId());
        assertThat(findPleNutMusic.isEmpty()).isTrue();
    }

    //pleNut 을 삭제하면 해당 pleNut 의 pleNutMusic 모두 삭제
    @Test
    void deletePleNut() {

        //given
        PlayNut playNut = new PlayNut();
        pleNutRepository.save(playNut);

        Song song = new Song();
        musicRepository.save(song);

        PlayNutMusic playNutMusic = new PlayNutMusic();
        playNutMusic.addPlaylistMusic(playNut, song);
        pleNutMusicRepository.save(playNutMusic);

        //when
        pleNutRepository.delete(playNut);

        //then
        Optional<PlayNutMusic> findPleNutMusic = pleNutMusicRepository.findById(playNutMusic.getId());
        assertThat(findPleNutMusic.isEmpty()).isTrue();
    }

    //회원을 삭제하면 해당 회원의 pleNut 과 pleNutMusic 모두 삭제
    @Test
    void deleteMember() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        PlayNut playNut = new PlayNut();
        playNut.createPleNut(member);
        pleNutRepository.save(playNut);

        Song song = new Song();
        musicRepository.save(song);

        PlayNutMusic playNutMusic = new PlayNutMusic();
        playNutMusic.addPlaylistMusic(playNut, song);
        pleNutMusicRepository.save(playNutMusic);

        //when
        memberRepository.delete(member);

        //then
        Optional<PlayNutMusic> findPleNutMusic = pleNutMusicRepository.findById(playNutMusic.getId());
        assertThat(findPleNutMusic.isEmpty()).isTrue();

        Optional<PlayNut> findPleNut = pleNutRepository.findById(playNut.getId());
        assertThat(findPleNut.isEmpty()).isTrue();
    }
}