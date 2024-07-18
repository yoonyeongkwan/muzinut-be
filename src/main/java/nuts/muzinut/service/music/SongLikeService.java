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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class SongLikeService {

    private final SongRepository songRepository;
    private final SongLikeRepository songLikeRepository;
    private final UserRepository userRepository;

    // 음악 좋아요
    public String getSongLike(Long songId){
        User user = getCurrentUsername();
        Optional<Song> songby = songRepository.findById(songId);
        Song song = songby.get();
        Optional<SongLike> bySongLike = songLikeRepository.findBySongLike(song, user);

        if(bySongLike.isPresent()){ // 사용자가 좋아요를 눌렀는지 확인 기능
            SongLike songLikeDelete = bySongLike.get();
            songLikeRepository.delete(songLikeDelete);
            return "좋아요 취소됬습니다";
        }else {
            SongLike songLike = new SongLike(user, song);
            songLikeRepository.save(songLike);
            return "좋아요 성공했습니다";
        }

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
