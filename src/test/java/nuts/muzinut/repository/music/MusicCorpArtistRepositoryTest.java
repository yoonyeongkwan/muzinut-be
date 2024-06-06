package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.Music;
import nuts.muzinut.domain.music.MusicCorpArtist;
import nuts.muzinut.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MusicCorpArtistRepositoryTest {

    @Autowired
    MusicRepository musicRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MusicCorpArtistRepository musicCorpArtistRepository;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        //음악 만드는데 협조한 아티스트
        Member corpArtist1 = new Member();
        Member corpArtist2 = new Member();
        memberRepository.save(corpArtist1);
        memberRepository.save(corpArtist2);

        Music music = new Music();
        music.addMusic(member);
        musicRepository.save(music);

        MusicCorpArtist corp1 = new MusicCorpArtist();
        MusicCorpArtist corp2 = new MusicCorpArtist();

        //when
        corp1.addCorpArtist(music, corpArtist1);
        corp2.addCorpArtist(music, corpArtist2);
        musicCorpArtistRepository.save(corp1);
        musicCorpArtistRepository.save(corp2);

        //then
        List<MusicCorpArtist> result = musicCorpArtistRepository.findAll();

        //저장된 음악은 1개
        assertThat(result)
                .extracting("music")
                .containsOnly(music);

        //1개의 음악에 대해서 협력한 가수는 2명
        assertThat(result)
                .extracting("memberId")
                .contains(corpArtist1.getId(), corpArtist2.getId());
    }

    @Test
    void delete() {

        //given
        MusicCorpArtist corp = new MusicCorpArtist();
        musicCorpArtistRepository.save(corp);

        //when
        musicCorpArtistRepository.delete(corp);

        //then
        Optional<MusicCorpArtist> findCorp = musicCorpArtistRepository.findById(corp.getId());
        assertThat(findCorp.isEmpty()).isTrue();
    }

    //음악을 삭제하면 해당 음악의 협력한 아티스트에 대한 내용도 삭제
    @Test
    void deleteMusic() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        //음악 만드는데 협조한 아티스트
        Member corpArtist1 = new Member();
        Member corpArtist2 = new Member();
        memberRepository.save(corpArtist1);
        memberRepository.save(corpArtist2);

        Music music = new Music();
        music.addMusic(member);
        musicRepository.save(music);

        MusicCorpArtist corp1 = new MusicCorpArtist();
        MusicCorpArtist corp2 = new MusicCorpArtist();

        corp1.addCorpArtist(music, corpArtist1);
        corp2.addCorpArtist(music, corpArtist2);
        musicCorpArtistRepository.save(corp1);
        musicCorpArtistRepository.save(corp2);

        //when
        musicRepository.delete(music);

        //then
        List<MusicCorpArtist> result = musicCorpArtistRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }
}