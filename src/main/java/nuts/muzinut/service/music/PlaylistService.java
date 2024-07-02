package nuts.muzinut.service.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.domain.music.PlaylistMusic;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.PlaylistRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    public List<PlaylistMusic> getPlaylist() {
        Long userId = getCurrentUserId();
        Optional<Playlist> findPlaylistOptional = playlistRepository.findByUserId(userId);
        if(!findPlaylistOptional.isEmpty()) {
            Playlist findPlaylist = findPlaylistOptional.get();
            List<PlaylistMusic> playlistMusics = findPlaylist.getPlaylistMusics();
            return playlistMusics;
        }
        return null;
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
}
