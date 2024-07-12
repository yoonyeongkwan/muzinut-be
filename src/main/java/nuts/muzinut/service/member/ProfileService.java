package nuts.muzinut.service.member;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Lounge;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Album;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.dto.board.DetailBaseDto;
import nuts.muzinut.dto.board.lounge.DetailLoungeDto;
import nuts.muzinut.dto.board.lounge.LoungeDto;
import nuts.muzinut.dto.board.lounge.LoungesForm;
import nuts.muzinut.dto.member.profile.Album.ProfileSongDto;
import nuts.muzinut.dto.member.profile.Album.ProfileAlbumDto;
import nuts.muzinut.dto.member.profile.Board.ProfileBoardDto;
import nuts.muzinut.dto.member.profile.Lounge.ProfileDetailLoungeDto;
import nuts.muzinut.dto.member.profile.Lounge.ProfileLoungeDto;
import nuts.muzinut.dto.member.profile.Lounge.ProfileLoungesForm;
import nuts.muzinut.dto.member.profile.ProfileDto;
import nuts.muzinut.exception.BoardNotExistException;
import nuts.muzinut.exception.BoardNotFoundException;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.repository.board.BoardRepository;
import nuts.muzinut.repository.board.LoungeRepository;
import nuts.muzinut.repository.board.query.LoungeQueryRepository;
import nuts.muzinut.repository.member.FollowRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.AlbumRepository;
import nuts.muzinut.repository.music.SongRepository;
import nuts.muzinut.service.board.DetailCommon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QLounge.lounge;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService extends DetailCommon {

    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final BoardRepository boardRepository;
    private final FollowRepository followRepository;
    private final LoungeRepository loungeRepository;
    private final LoungeQueryRepository queryRepository;


    // 프로필 페이지 보여주는 메소드
    public ProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));
        validateUser(user);
        // 팔로잉 수, 팔로워 수 가져오기
        Long followingCount = followRepository.countFollowingByUser(user);
        Long followersCount = followRepository.countFollowerByFollowingMemberId(userId);

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
            isFollowing = followRepository.existsByUserAndFollowingMemberId(user, userId);
            log.info("isFollowing = {}", isFollowing);
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

    // 앨범 탭을 보여주는 메소드
    public ProfileSongDto getAlbumTab(Long userId) {
        List<Song> songs = songRepository.findSongsByUserIdOrderByLikesAndId(userId);
        ProfileDto profileDto = getUserProfile(userId);

        if (songs.isEmpty()) {
            return new ProfileSongDto(
                    encodeFileToBase64(profileDto.getProfileBannerImgName(), true),
                    encodeFileToBase64(profileDto.getProfileImgName(), false),
                    profileDto.getNickname(),
                    profileDto.getIntro(),
                    profileDto.getFollowingCount(),
                    profileDto.getFollowersCount(),
                    profileDto.isFollowStatus()
            );
        }

        Song mainSong = songs.get(0);
        String songGenre = mainSong.getSongGenres().stream()
                .map(genre -> genre.getGenre().toString())
                .collect(Collectors.joining(", "));

        Album album = mainSong.getAlbum();
        String albumImg = (album != null) ? album.getAlbumImg() : null;

        List<ProfileAlbumDto> allAlbums = albumRepository.findAllByUserIdOrderByLatest(userId).stream()
                .map(a -> new ProfileAlbumDto(
                        encodeAlbumFileToBase64(a.getAlbumImg()),
                        a.getName()
                ))
                .collect(Collectors.toList());

        return new ProfileSongDto(
                encodeFileToBase64(profileDto.getProfileBannerImgName(), true),
                encodeFileToBase64(profileDto.getProfileImgName(), false),
                profileDto.getNickname(),
                profileDto.getIntro(),
                profileDto.getFollowingCount(),
                profileDto.getFollowersCount(),
                profileDto.isFollowStatus(),
                mainSong.getTitle(),
                songGenre,
                mainSong.getLyricist(),
                mainSong.getComposer(),
                mainSong.getSongLikes().size(),
                encodeAlbumFileToBase64(albumImg),
                allAlbums
        );
    }

    // 라운지 탭을 보여주는 메소드
    public ProfileLoungeDto getLoungeTab(Long userId, int startPage) throws BoardNotExistException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));

        PageRequest pageRequest = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt"));
        Page<Lounge> page = loungeRepository.findAllByUserId(userId, pageRequest);
        List<Lounge> lounges = page.getContent();

        ProfileDto profileDto = getUserProfile(userId);

        if (lounges.isEmpty()){
            return new ProfileLoungeDto(
                    encodeFileToBase64(profileDto.getProfileBannerImgName(), true),
                    encodeFileToBase64(profileDto.getProfileImgName(), false),
                    profileDto.getNickname(),
                    profileDto.getIntro(),
                    profileDto.getFollowingCount(),
                    profileDto.getFollowersCount(),
                    profileDto.isFollowStatus()
            );
        }
        List<ProfileLoungesForm> loungesForms = lounges.stream()
                .map(l -> new ProfileLoungesForm(
                        l.getId(),
                        l.getUser().getNickname(),
                        l.getFilename(),
                        l.getCreatedDt(),
                        l.getLikes().size(),
                        l.getComments().size()
                ))
                .collect(Collectors.toList());

        return new ProfileLoungeDto(
                encodeFileToBase64(profileDto.getProfileBannerImgName(), true),
                encodeFileToBase64(profileDto.getProfileImgName(), false),
                profileDto.getNickname(),
                profileDto.getIntro(),
                profileDto.getFollowingCount(),
                profileDto.getFollowersCount(),
                profileDto.isFollowStatus(),
                loungesForms,
                startPage,
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    // 특정 라운지 게시판 댓글, 대댓글 조회 메소드
    public ProfileDetailLoungeDto detailLounge(Long boardId, User user) {
        List<Tuple> result = queryRepository.getDetailLounge(boardId, user);

        log.info("tuple: {}", result);
        if (result.isEmpty()) {
            throw new BoardNotFoundException("라운지 게시판이 존재하지 않습니다.");
        }

        Tuple first = result.getFirst();
        Board findBoard = first.get(board);
        Lounge findLounge = first.get(lounge);

        if (findBoard == null || findLounge == null) {
            return null;
        }

        // getUserProfile 메소드 호출
        ProfileDto profileDto = getUserProfile(findLounge.getUser().getId());

        ProfileDetailLoungeDto profileDetailLoungeDto = new ProfileDetailLoungeDto(
                findLounge.getId(),
                findLounge.getUser().getNickname(),
                findLounge.getFilename(),
                encodeFileToBase64(profileDto.getProfileBannerImgName(), true),
                encodeFileToBase64(profileDto.getProfileImgName(), false),
                profileDto.getNickname(),
                profileDto.getIntro(),
                profileDto.getFollowingCount(),
                profileDto.getFollowersCount(),
                profileDto.isFollowStatus()
        );

        DetailBaseDto detailBaseDto = first.get(2, DetailBaseDto.class);
        profileDetailLoungeDto.setLikeCount(findBoard.getLikeCount()); //좋아요 수 셋팅
        profileDetailLoungeDto.setBoardLikeStatus(detailBaseDto.getBoardLikeStatus()); //사용자가 특정 게시판의 좋아요를 눌렀는지 여부
        profileDetailLoungeDto.setIsBookmark(detailBaseDto.getIsBookmark()); //사용자가 특정 게시판을 북마크했는지 여부

        //게시판 댓글 & 대댓글 셋팅
        profileDetailLoungeDto.setComments(setCommentsAndReplies(user, findBoard));

        return profileDetailLoungeDto;
    }

    // 게시글 탭을 보여주는 메소드
    public ProfileBoardDto getBoardTab(Long userId) {
        List<String> boardsTitle = userRepository.findBoardTitlesByUserId(userId);
        List<String> bookmarkTitle = userRepository.findBookmarkTitlesByUserId(userId);
        ProfileDto profileDto = getUserProfile(userId);

        return new ProfileBoardDto(
                encodeFileToBase64(profileDto.getProfileBannerImgName(), true),
                encodeFileToBase64(profileDto.getProfileImgName(), false),
                profileDto.getNickname(),
                profileDto.getIntro(),
                profileDto.getFollowingCount(),
                profileDto.getFollowersCount(),
                profileDto.isFollowStatus(),
                boardsTitle,
                bookmarkTitle
        );
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

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("유효하지 않은 유저 정보입니다.");
        }
    }
}
