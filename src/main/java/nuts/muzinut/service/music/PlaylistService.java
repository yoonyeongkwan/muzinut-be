package nuts.muzinut.service.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.domain.music.PlaylistMusic;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.dto.music.playlist.PlaylistMusicsDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.PlaylistMusicRepository;
import nuts.muzinut.repository.music.PlaylistRepository;
import nuts.muzinut.repository.music.SongRepository;
import nuts.muzinut.repository.music.query.PlaylistQueryRepository;
import nuts.muzinut.service.encoding.EncodingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
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

    @Value("${spring.file.dir}")
    private String fileDir;

    public List<PlaylistMusicsDto> getPlaylistMusics() throws IOException {
        Long userId = getCurrentUserId();
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
    private Long getCurrentUserId() {
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

    public void addPlaylistMusics(List<Long> addList) {
        Long userId = getCurrentUserId();
        Playlist playlist = playlistRepository.findByUserId(userId).get();

        for (Long l : addList) {
            PlaylistMusic playlistMusic = new PlaylistMusic();
            playlistMusic.addRecord(playlist, l);
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
}
