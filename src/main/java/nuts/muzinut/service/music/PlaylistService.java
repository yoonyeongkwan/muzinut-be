package nuts.muzinut.service.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.*;
import nuts.muzinut.dto.music.playlist.PlaylistMusicsDto;
import nuts.muzinut.exception.EntityOversizeException;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.*;
import nuts.muzinut.repository.music.query.PlaylistQueryRepository;
import nuts.muzinut.service.encoding.EncodingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistQueryRepository playlistQueryRepository;
    private final UserRepository userRepository;
    private final EncodingService encodingService;
    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository playlistMusicRepository;
    private final PlayNutRepository playNutRepository;
    private final PlayNutMusicRepository playNutMusicRepository;

    @Value("${spring.file.dir}")
    private String fileDir;

    public List<PlaylistMusicsDto> getPlaylistMusics(Long userId) throws IOException {
        List<PlaylistMusicsDto> playlistMusics = playlistQueryRepository.getPlaylistMusics(userId);

        for(PlaylistMusicsDto dto : playlistMusics) {
            String img = dto.getAlbumImg();
            String imagePath = fileDir + "/albumImg/" + img;
            File file = new File(imagePath);
            String encodingImage = encodingService.encodingBase64(file);
            dto.setAlbumImg(encodingImage);
        }

        return playlistMusics;
    }

    // 현재 인증된 사용자의 userId를 반환하는 메소드
    public Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String Username =  ((UserDetails) principal).getUsername();
            Optional<User> finduser = userRepository.findOneWithAuthoritiesByUsername(Username);
            User user = finduser.get();
            return user.getId();
        } else {
            return -1L;
        }
    }

    public void addSongIsExist(List<Long> addList) {
        for (Long l : addList) {
            songRepository.findById(l).orElseThrow(
                    () -> new NotFoundEntityException(l + "에 해당하는 음원이 존재하지 않습니다."));
        }
    }

    public void addPlaylistMusics(List<Long> addList, Long userId) {
        Playlist playlist = playlistRepository.findByUserId(userId).get();
        List<Song> addSongList = new ArrayList<>();
        for (Long l : addList) {
            // songId 로 Song 객체 탐색
            Song song = songRepository.findById(l).get();
            addSongList.add(song);
        }
        for (Song s : addSongList) {
            PlaylistMusic playlistMusic = new PlaylistMusic();
            playlistMusic.addRecord(playlist, s);
        }
    }

    public void deleteSongIsExist(List<Long> deleteList) {
        for (Long l : deleteList) {
            playlistMusicRepository.findById(l).orElseThrow(
                    () -> new NotFoundEntityException(l + "에 해당하는 PlaylistMusic 이 존재하지 않습니다."));
        }
    }

    public void deletePlaylistMusics(List<Long> deleteList) {
        for (Long l : deleteList) {
            playlistMusicRepository.deleteById(l);
        }
    }

    public void deleteAllPlaylistMusics() {
        Long userId = getCurrentUserId();
        Playlist playlist = playlistRepository.findByUserId(userId).get();
        playlistMusicRepository.deleteAllPlaylistMusic(playlist);
    }

    public void checkPlaylistMaxSize(int addSize, Long userId) {
        Playlist playlist = playlistRepository.findByUserId(userId).get();
        // 플레이리스트의 최대 저장 갯수 1000 개를 확인하는 메소드
        List<Long> storePlaylistMusicsCount = playlistQueryRepository.getStorePlaylistMusicsCount(playlist);
        if(storePlaylistMusicsCount.get(0) + addSize > 1000) throw new EntityOversizeException("플레이리스트의 최대 저장 갯수를 초과하였습니다. 현재 플레이리스트 갯수 : " + storePlaylistMusicsCount.get(0));
    }

    public PlayNut findPlaynut(Long changePlaynutId, Long userId) {
        User user = userRepository.findById(userId).get();
        playNutRepository.findByIdAndUser(changePlaynutId, user).orElseThrow(
                () -> new NotFoundEntityException("해당 playnut 이 존재하지 않습니다.")
        );
        return playNutRepository.findByIdAndUser(changePlaynutId, user).get();
    }

    public List<Long> findAllPlaynutMusics(PlayNut playnut) {
        List<PlayNutMusic> allPlayNutMusic = playNutMusicRepository.findAllByPlayNut(playnut);
        List<Long> playnutMusicList = new ArrayList<>();
        for (PlayNutMusic playNutMusic : allPlayNutMusic) {
            playnutMusicList.add(playNutMusic.getSongId());
        }

        return playnutMusicList;
    }
}
