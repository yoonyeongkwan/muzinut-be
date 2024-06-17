package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.repository.member.UserRepository;
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
    UserRepository userRepository;
    @Autowired
    PlaylistRepository playlistRepository;

    @Test
    void save() {

        //given
        User user = new User();
        userRepository.save(user);
        Playlist playlist = new Playlist();

        //when
        playlist.createPlaylist(user);
        playlistRepository.save(playlist);

        //then
        Optional<Playlist> findPlaylist = playlistRepository.findById(playlist.getId());
        assertThat(findPlaylist.get()).isEqualTo(playlist);
        assertThat(findPlaylist.get().getUser()).isEqualTo(user);
    }

    @Test
    void delete() {

        //given
        User user = new User();
        userRepository.save(user);

        Playlist playlist = new Playlist();
        playlist.createPlaylist(user);
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
        User user = new User();
        userRepository.save(user);

        Playlist playlist = new Playlist();
        playlist.createPlaylist(user);
        playlistRepository.save(playlist);

        //when
        userRepository.delete(user);

        //then
        Optional<Playlist> findPlaylist = playlistRepository.findById(playlist.getId());
        assertThat(findPlaylist.isEmpty()).isTrue();
    }
}