package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.Music;
import nuts.muzinut.domain.music.PleNut;
import nuts.muzinut.domain.music.PleNutMusic;
import nuts.muzinut.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PleNutMusicRepositoryTest {

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
        PleNut pleNut = new PleNut();
        pleNutRepository.save(pleNut);

        Music music = new Music();
        musicRepository.save(music);

        PleNutMusic pleNutMusic = new PleNutMusic();
        pleNutMusic.addPlaylistMusic(pleNut, music);

        //when
        pleNutMusicRepository.save(pleNutMusic);

        //then
        Optional<PleNutMusic> findPleNutMusic = pleNutMusicRepository.findById(pleNutMusic.getId());
        assertThat(findPleNutMusic.get()).isEqualTo(pleNutMusic);
        assertThat(findPleNutMusic.get().getPleNut()).isEqualTo(pleNut);
    }

    @Test
    void delete() {

        //given
        PleNutMusic pleNutMusic = new PleNutMusic();
        pleNutMusicRepository.save(pleNutMusic);

        //when
        pleNutMusicRepository.delete(pleNutMusic);

        //then
        Optional<PleNutMusic> findPleNutMusic = pleNutMusicRepository.findById(pleNutMusic.getId());
        assertThat(findPleNutMusic.isEmpty()).isTrue();
    }

    //pleNut 을 삭제하면 해당 pleNut 의 pleNutMusic 모두 삭제
    @Test
    void deletePleNut() {

        //given
        PleNut pleNut = new PleNut();
        pleNutRepository.save(pleNut);

        Music music = new Music();
        musicRepository.save(music);

        PleNutMusic pleNutMusic = new PleNutMusic();
        pleNutMusic.addPlaylistMusic(pleNut, music);
        pleNutMusicRepository.save(pleNutMusic);

        //when
        pleNutRepository.delete(pleNut);

        //then
        Optional<PleNutMusic> findPleNutMusic = pleNutMusicRepository.findById(pleNutMusic.getId());
        assertThat(findPleNutMusic.isEmpty()).isTrue();
    }

    //회원을 삭제하면 해당 회원의 pleNut 과 pleNutMusic 모두 삭제
    @Test
    void deleteMember() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        PleNut pleNut = new PleNut();
        pleNut.createPleNut(member);
        pleNutRepository.save(pleNut);

        Music music = new Music();
        musicRepository.save(music);

        PleNutMusic pleNutMusic = new PleNutMusic();
        pleNutMusic.addPlaylistMusic(pleNut, music);
        pleNutMusicRepository.save(pleNutMusic);

        //when
        memberRepository.delete(member);

        //then
        Optional<PleNutMusic> findPleNutMusic = pleNutMusicRepository.findById(pleNutMusic.getId());
        assertThat(findPleNutMusic.isEmpty()).isTrue();

        Optional<PleNut> findPleNut = pleNutRepository.findById(pleNut.getId());
        assertThat(findPleNut.isEmpty()).isTrue();
    }
}