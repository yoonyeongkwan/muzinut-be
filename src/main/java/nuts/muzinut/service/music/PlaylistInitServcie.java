package nuts.muzinut.service.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Album;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.domain.music.PlaylistMusic;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.AlbumRepository;
import nuts.muzinut.repository.music.PlaylistMusicRepository;
import nuts.muzinut.repository.music.PlaylistRepository;
import nuts.muzinut.repository.music.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaylistInitServcie {
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository playlistMusicRepository;

    public void playListInit() {
        User user1 = userRepository.findById(1L).get();
        User user2 = new User("전영호", "123123","전영호");
        User user3 = new User("전영호2", "123123123","전영호2");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Playlist playlist = new Playlist();
        playlist.createPlaylist(user1);
        playlistRepository.save(playlist);

        Album album = new Album(user2, "앨범이름1", "앨범소개1","이미지.jpg");
        albumRepository.save(album);

        Song song1 = new Song(user2, "제목1", "가사1", "작사가1", "작곡가1", "음원1.mp3", album);
        Song song2 = new Song(user2, "제목2", "가사2", "작사가2", "작곡가2", "음원2.mp3", album);
        Song song3 = new Song(user2, "제목3", "가사3", "작사가3", "작곡가3", "음원3.mp3", album);
        songRepository.save(song1);
        songRepository.save(song2);
        songRepository.save(song3);

        PlaylistMusic playlistMusic1 = new PlaylistMusic();
        PlaylistMusic playlistMusic2 = new PlaylistMusic();
        PlaylistMusic playlistMusic3 = new PlaylistMusic();
        playlistMusic1.addRecord(playlist, song1.getId());
        playlistMusic2.addRecord(playlist, song2.getId());
        playlistMusic3.addRecord(playlist, song3.getId());
        playlistMusicRepository.save(playlistMusic1);
        playlistMusicRepository.save(playlistMusic2);
        playlistMusicRepository.save(playlistMusic3);

        PlaylistMusic playlistMusic4 = new PlaylistMusic();
        playlistMusic4.addRecord(user2.getPlaylist(), song2.getId());
    }

    public void hotArtistInit() {
        User user1 = userRepository.findById(1L).get();
        User user2 = userRepository.findById(2L).get();
        User user3 = userRepository.findById(3L).get();
        User user4 = userRepository.findById(4L).get();
        User user5 = userRepository.findById(5L).get();
        User user6 = userRepository.findById(6L).get();

        Follow follow1 = new Follow();
        follow1.createFollowing(user2, user1);
        Follow follow2 = new Follow();
        follow2.createFollowing(user3, user1);
        Follow follow3 = new Follow();
        follow3.createFollowing(user4, user1);
        Follow follow4 = new Follow();
        follow4.createFollowing(user5, user1);
    }
}
