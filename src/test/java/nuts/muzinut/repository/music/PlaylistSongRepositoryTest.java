package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.domain.music.PlaylistMusic;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PlaylistSongRepositoryTest {

    @Autowired
    PlaylistRepository playlistRepository;
    @Autowired
    PlaylistMusicRepository playlistMusicRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SongRepository musicRepository;

    @Test
    void save() {

        //given
        Playlist playlist = new Playlist();
        playlistRepository.save(playlist);

        Song song = new Song();
        musicRepository.save(song);

        PlaylistMusic playlistMusic = new PlaylistMusic();
        playlistMusic.addRecord(playlist, song);

        //when
        playlistMusicRepository.save(playlistMusic);

        //then
        Optional<PlaylistMusic> findPlaylistMusic = playlistMusicRepository.findById(playlistMusic.getId());
        assertThat(findPlaylistMusic.get()).isEqualTo(playlistMusic);
        assertThat(findPlaylistMusic.get().getMusicId()).isEqualTo(song.getId());
        assertThat(findPlaylistMusic.get().getPlaylist()).isEqualTo(playlist);
    }

    @Test
    void delete() {

        //given
        PlaylistMusic playlistMusic = new PlaylistMusic();
        playlistMusicRepository.save(playlistMusic);

        //when
        playlistMusicRepository.delete(playlistMusic);

        //then
        Optional<PlaylistMusic> findPlaylistMusic = playlistMusicRepository.findById(playlistMusic.getId());
        assertThat(findPlaylistMusic.isEmpty()).isTrue();
    }

    //playlist 가 삭제되면 playlistMusic 모두 삭제
    @Test
    void deletePlaylist() {

        //given
        Playlist playlist = new Playlist();
        playlistRepository.save(playlist);

        Song song = new Song();
        musicRepository.save(song);

        PlaylistMusic playlistMusic = new PlaylistMusic();
        playlistMusic.addRecord(playlist, song);
        playlistMusicRepository.save(playlistMusic);

        //when
        playlistRepository.delete(playlist);

        //then
        Optional<PlaylistMusic> findPlaylistMusic = playlistMusicRepository.findById(playlistMusic.getId());
        assertThat(findPlaylistMusic.isEmpty()).isTrue();
    }

    //member 가 삭제되면 playlist, playlistMusic 모두 삭제
    @Test
    void deleteMember() {

        //given
        User user = new User();
        userRepository.save(user);

        Playlist playlist = new Playlist();
        playlist.createPlaylist(user);
        playlistRepository.save(playlist);

        Song song = new Song();
        musicRepository.save(song);

        PlaylistMusic playlistMusic = new PlaylistMusic();
        playlistMusic.addRecord(playlist, song);
        playlistMusicRepository.save(playlistMusic);

        //when
        userRepository.delete(user);

        //then
        Optional<PlaylistMusic> findPlaylistMusic = playlistMusicRepository.findById(playlistMusic.getId());
        assertThat(findPlaylistMusic.isEmpty()).isTrue();

        Optional<Playlist> findPlaylist = playlistRepository.findById(playlistMusic.getId());
        assertThat(findPlaylist.isEmpty()).isTrue();
    }
}