package nuts.muzinut.service.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.domain.music.SongLike;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.SongLikeRepository;
import nuts.muzinut.repository.music.SongRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SongLikeService {

    private  final SongRepository songRepository;
    private final SongLikeRepository songLikeRepository;
    private final UserRepository userRepository;

    public void getSongLike(Long songId){
        User user = getCurrentUsername();
        Optional<Song> songby = songRepository.findById(songId);
        Song song = songby.get();
        SongLike songLike = new SongLike(user, song);
        songLikeRepository.save(songLike);

        Long count = songLikeRepository.countLikesBySongId(songId);
        System.out.println("count = " + count);
    }

    // 현재 인증된 사용자의 이름을 반환하는 메소드
    private User getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String Username =  ((UserDetails) principal).getUsername();
            Optional<User> finduser = userRepository.findOneWithAuthoritiesByUsername(Username);
            User user = finduser.get();
            return user;
        } else {
            return null;
        }
    }
}
