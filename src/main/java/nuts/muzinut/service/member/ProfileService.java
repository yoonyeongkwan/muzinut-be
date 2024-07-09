package nuts.muzinut.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Lounge;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Album;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.dto.member.profile.ProfileSongDto;
import nuts.muzinut.dto.member.profile.ProfileAlbumListDto;
import nuts.muzinut.dto.member.profile.ProfileDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.repository.board.BoardRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.AlbumRepository;
import nuts.muzinut.repository.music.SongRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final FollowService followService;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final BoardRepository boardRepository;

    // 프로필 페이지 보여주는 메소드
    public ProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));

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

    // 메인 곡을 보여주는 메소드
    public ProfileSongDto getMainSong(Long userId) {
        List<Song> songs = songRepository.findSongsByUserIdOrderByLikesAndId(userId);

        if (songs.isEmpty()) {
            return null; // 곡이 없을 경우 처리
        }

        Song mainSong = songs.get(0); // 좋아요 수가 가장 높은 곡 또는 첫 번째 등록된 곡

        String songGenre = mainSong.getSongGenres().stream()
                .map(genre -> genre.getGenre().toString())
                .collect(joining(", "));

        Album album = mainSong.getAlbum();
        String albumImg = (album != null) ? album.getAlbumImg() : null;

        return new ProfileSongDto(
                mainSong.getTitle(),
                songGenre,
                mainSong.getLyricist(),
                mainSong.getComposer(),
                mainSong.getSongLikes().size(),
                albumImg // 앨범 image 추가
        );
    }

    // 앨범 리스트를 보여주는 메소드
    public List<ProfileAlbumListDto> getAllAlbums(Long userId) {
        // 사용자의 모든 앨범을 가져오는 로직을 작성합니다.
        return albumRepository.findAllByUserIdOrderByLatest(userId).stream()
                .map(album -> new ProfileAlbumListDto(album.getAlbumImg(), album.getName()))
                .collect(toList());
    }

    // 게시물 상세 정보를 가져오는 메소드
    public Map<String, Object> getBoardDetails(Long id) {
        Map<String, Object> postDetails = new HashMap<>();

        // 게시판 유형 조회
        Optional<Board> board = boardRepository.findById(id);
        if (board.isEmpty()) {
            throw new IllegalArgumentException("Invalid board ID: " + id);
        }

        String boardType = boardRepository.findBoardTypeById(id);
        log.info("boardType = {}", boardType);

        postDetails.put("boardType", boardType);
        return postDetails;
    }

    // 라운지 생성하는 메소드
    public Lounge save(Lounge lounge) {
        return loungeRepository.save(lounge);
    }
}
