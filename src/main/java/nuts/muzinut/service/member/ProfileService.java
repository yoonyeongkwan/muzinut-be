package nuts.muzinut.service.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Album;
import nuts.muzinut.domain.music.Genre;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.domain.music.SongGenre;
import nuts.muzinut.dto.member.ProfileDto;
import nuts.muzinut.dto.music.AlbumDto;
import nuts.muzinut.dto.music.SongDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.AlbumRepository;
import nuts.muzinut.service.board.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final FollowService followService;
    private final AlbumRepository albumRepository;

    // 프로필 페이지 보여주는 메소드
    public ProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다"));

        // 팔로잉 수, 팔로워 수 가져오기
        Long followingCount = followService.countFollowing(user);
        Long followersCount = followService.countFollowers(userId);

        // 현재 로그인한 사용자 확인
        String currentUsername = getCurrentUsername();
        log.info("currentUsername = {}", currentUsername);
        User currentUser = null;
        if (currentUsername.equals("anonymousUser")){
            currentUser = null;
        }else {
            currentUser = findUserByUsername(currentUsername);
        }

        // 팔로잉 여부
        boolean isFollowing = false;
        if (currentUser != null) {
            isFollowing = followService.isFollowing(currentUser, userId);
            log.info("isFollowing = {}", isFollowing);
        }

        if ( user.getProfileBannerImgFilename() == null) {
            user.setProfileBannerImgFilename("bannerBase.png");
        }
        if ( user.getProfileImgFilename() == null) {
            user.setProfileImgFilename("base.png");
        }

        ProfileDto profileDto = new ProfileDto(
                user.getProfileBannerImgFilename(),
                user.getProfileImgFilename(),
                user.getNickname(),
                user.getIntro(),
                followingCount,
                followersCount,
                isFollowing
        );

        return profileDto;
    }

    // 사용자가 작성한 게시글 제목을 가져오는 메소드
    public List<String> getUserBoardTitles(Long userId) {
        // 인증된 사용자 정보 가져오기
        String username = getCurrentUsername();
        User user = userRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new NotFoundEntityException("Invalid user"));

        // 유저가 작성한 게시글 제목 리스트 반환
        return userRepository.findBoardTitlesByUserId(userId);
    }

    // 현재 인증된 사용자의 이름을 반환하는 메소드
    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    // 토큰의 사용자 반환
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("접근 권한이 없습니다."));
    }

    // 사용자가 업로드한 앨범 가져오는 메소드
    public List<AlbumDto> getUserAlbums(Long userId) {
        List<Album> albums = albumRepository.findByUserId(userId);
        return albums.stream()
                .map(this::convertToAlbumDto)
                .collect(Collectors.toList());
    }

    // 변환 메소드 추가
    private SongDto convertToSongDto(Song song) {
        List<String> genres = song.getSongGenres().stream()
                .map(songGenre -> songGenre.getGenre().name())
                .collect(Collectors.toList());

        return new SongDto(
                song.getTitle(),
                song.getLyricist(),
                song.getComposer(),
                genres,
                song.getLyrics(),
                song.getFileName()
        );
    }

    private AlbumDto convertToAlbumDto(Album album) {
        List<SongDto> songDtos = album.getSongList().stream()
                .map(this::convertToSongDto)
                .collect(Collectors.toList());

        return new AlbumDto(
                album.getName(),
                album.getIntro(),
                songDtos
        );
    }

}
