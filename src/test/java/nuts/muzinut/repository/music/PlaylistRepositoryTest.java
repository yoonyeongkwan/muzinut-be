package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PlaylistRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PlaylistRepository playlistRepository;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);
        Playlist playlist = new Playlist();

        //when
        playlist.createPlaylist(member);
        playlistRepository.save(playlist);

        //then
        Optional<Playlist> findPlaylist = playlistRepository.findById(playlist.getId());
        assertThat(findPlaylist.get()).isEqualTo(playlist);
        assertThat(findPlaylist.get().getMember()).isEqualTo(member);
    }

    @Test
    void delete() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Playlist playlist = new Playlist();
        playlist.createPlaylist(member);
        playlistRepository.save(playlist);

        //when
        playlistRepository.delete(playlist);

        //then
        Optional<Playlist> findPlaylist = playlistRepository.findById(playlist.getId());
        assertThat(findPlaylist.isEmpty()).isTrue();
    }

    //회원이 삭제되면 그 회원에 해당하는 플레이리스트도 삭제
    @Test
    void deleteMember() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Playlist playlist = new Playlist();
        playlist.createPlaylist(member);
        playlistRepository.save(playlist);

        //when
        memberRepository.delete(member);

        //then
        Optional<Playlist> findPlaylist = playlistRepository.findById(playlist.getId());
        assertThat(findPlaylist.isEmpty()).isTrue();
    }
}