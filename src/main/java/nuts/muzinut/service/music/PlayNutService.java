package nuts.muzinut.service.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.PlayNut;
import nuts.muzinut.domain.music.PlayNutMusic;
import nuts.muzinut.dto.music.PlayNutDto;
import nuts.muzinut.dto.music.PlayNutMusicDto;
import nuts.muzinut.dto.music.PlaynutTitleDto;
import nuts.muzinut.exception.LimitPlayNutException;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.PlayNutMusicRepository;
import nuts.muzinut.repository.music.PlayNutRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayNutService {

    private final UserRepository userRepository;
    private final PlayNutRepository playNutRepository;
    private final PlayNutMusicRepository playNutMusicRepository;

    // 플리넛 디렉토리 생성
    public void savePlayNut(PlaynutTitleDto data) {
        User user = getUser();
        List<PlayNutDto> playNutDirCount = playNutRepository.findByPlayNut(user);
        if (playNutDirCount.size() < 10){
            PlayNut playNut = new PlayNut(data.getTitle(), user);
            playNutRepository.save(playNut);
        }else {
            throw new LimitPlayNutException("플리넛 생성은 10개 까지입니다");
        }

    }

    // 플리넛의 곡 추가
    public void saveMusic(Long playNutId, Long songId) {
        List<PlayNutMusicDto> findMusicCount = playNutMusicRepository.findPlayNutMusic(playNutId);
        if (findMusicCount.size() < 1000){
            Optional<PlayNut> optional = playNutRepository.findById(playNutId);
            PlayNut playNut = optional.get();
            PlayNutMusic playNutMusic = new PlayNutMusic(playNut, songId);
            playNutMusicRepository.save(playNutMusic);
        }else {
            throw new LimitPlayNutException("플리넛 곡 추가는 1000개 까지입니다");
        }

    }

    // 플리넛 디렉토리 이름 변경
    public void updatePlayNut(Long playNutId, PlaynutTitleDto data) {
        playNutRepository.updateByTitle(data.getTitle(), playNutId);
    }

    // 플리넛 디렉토리 삭제
    public void playNutDelete(Long playNutId) {
        playNutRepository.deleteById(playNutId);
    }

    // 플리넛 곡 삭제
    public void playNutMusicDelete(Long playNutMusicId) {
        playNutMusicRepository.deleteById(playNutMusicId);
    }

    // 플리넛 디렉토리 조회
    public ResponseEntity<List<PlayNutDto>> findPlayNutDir() {
        User user = getUser();
        List<PlayNutDto> playNutDir = playNutRepository.findByPlayNut(user);
        if (playNutDir.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PlayNutDto>>(playNutDir, HttpStatus.OK);
    }

    // 플리넛 곡 조회
    public ResponseEntity<List<PlayNutMusicDto>> findPlayNutMusic(Long playNutId) {
        List<PlayNutMusicDto> totalData = playNutMusicRepository.findPlayNutMusic(playNutId);
        if (totalData.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PlayNutMusicDto>>(totalData, HttpStatus.OK);
    }

    // 현재 인증된 사용자의 이름을 반환하는 메소드
    private User getUser() {
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
