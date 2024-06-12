package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.domain.music.SongCorpArtist;
import nuts.muzinut.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class SongCorpArtistRepositoryTest {

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

        Song song = new Song();
        song.createMusic(member);
        musicRepository.save(song);

        SongCorpArtist corp1 = new SongCorpArtist();
        SongCorpArtist corp2 = new SongCorpArtist();

        //when
        corp1.addCorpArtist(song, corpArtist1);
        corp2.addCorpArtist(song, corpArtist2);
        musicCorpArtistRepository.save(corp1);
        musicCorpArtistRepository.save(corp2);

        //then
        List<SongCorpArtist> result = musicCorpArtistRepository.findAll();

        //저장된 음악은 1개
        assertThat(result)
                .extracting("song")
                .containsOnly(song);

        //1개의 음악에 대해서 협력한 가수는 2명
        assertThat(result)
                .extracting("memberId")
                .contains(corpArtist1.getId(), corpArtist2.getId());
    }

    @Test
    void delete() {

        //given
        SongCorpArtist corp = new SongCorpArtist();
        musicCorpArtistRepository.save(corp);

        //when
        musicCorpArtistRepository.delete(corp);

        //then
        Optional<SongCorpArtist> findCorp = musicCorpArtistRepository.findById(corp.getId());
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

        Song song = new Song();
        song.createMusic(member);
        musicRepository.save(song);

        SongCorpArtist corp1 = new SongCorpArtist();
        SongCorpArtist corp2 = new SongCorpArtist();

        corp1.addCorpArtist(song, corpArtist1);
        corp2.addCorpArtist(song, corpArtist2);
        musicCorpArtistRepository.save(corp1);
        musicCorpArtistRepository.save(corp2);

        //when
        musicRepository.delete(song);

        //then
        List<SongCorpArtist> result = musicCorpArtistRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }
}