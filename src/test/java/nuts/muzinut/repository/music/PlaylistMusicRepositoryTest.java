package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.Music;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.domain.music.PlaylistMusic;
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
class PlaylistMusicRepositoryTest {

    @Autowired
    PlaylistRepository playlistRepository;
    @Autowired
    PlaylistMusicRepository playlistMusicRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MusicRepository musicRepository;

    @Test
    void save() {

        //given
        Playlist playlist = new Playlist();
        playlistRepository.save(playlist);

        Music music = new Music();
        musicRepository.save(music);

        PlaylistMusic playlistMusic = new PlaylistMusic();
        playlistMusic.addRecord(playlist, music);

        //when
        playlistMusicRepository.save(playlistMusic);

        //then
        Optional<PlaylistMusic> findPlaylistMusic = playlistMusicRepository.findById(playlistMusic.getId());
        assertThat(findPlaylistMusic.get()).isEqualTo(playlistMusic);
        assertThat(findPlaylistMusic.get().getMusicId()).isEqualTo(music.getId());
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

        Music music = new Music();
        musicRepository.save(music);

        PlaylistMusic playlistMusic = new PlaylistMusic();
        playlistMusic.addRecord(playlist, music);
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
        Member member = new Member();
        memberRepository.save(member);

        Playlist playlist = new Playlist();
        playlist.createPlaylist(member);
        playlistRepository.save(playlist);

        Music music = new Music();
        musicRepository.save(music);

        PlaylistMusic playlistMusic = new PlaylistMusic();
        playlistMusic.addRecord(playlist, music);
        playlistMusicRepository.save(playlistMusic);

        //when
        memberRepository.delete(member);

        //then
        Optional<PlaylistMusic> findPlaylistMusic = playlistMusicRepository.findById(playlistMusic.getId());
        assertThat(findPlaylistMusic.isEmpty()).isTrue();

        Optional<Playlist> findPlaylist = playlistRepository.findById(playlistMusic.getId());
        assertThat(findPlaylist.isEmpty()).isTrue();
    }
}