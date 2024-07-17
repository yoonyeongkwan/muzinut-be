package nuts.muzinut.service.mypick;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.mypick.MypickComment;
import nuts.muzinut.dto.mypick.FindArtistDto;
import nuts.muzinut.dto.mypick.MyPickCommentDto;
import nuts.muzinut.dto.mypick.MyPickRankingDto;
import nuts.muzinut.exception.LackVoteAmountException;
import nuts.muzinut.exception.NoDataFoundException;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.mypick.MypickCommentRepository;
import nuts.muzinut.repository.mypick.query.MyPickQueryRepository;
import nuts.muzinut.service.encoding.EncodingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class MyPickService {
    private final MyPickQueryRepository myPickQueryRepository;
    private final EncodingService encodingService;
    private final UserRepository userRepository;
    private final MypickCommentRepository mypickCommentRepository;
    @Value("${spring.file.dir}")
    private String fileDir;
    public List<FindArtistDto> findArtist(String findNickname) throws IOException {
        List<FindArtistDto> findArtistList = myPickQueryRepository.findArtist(findNickname);
        for (FindArtistDto findArtistDto : findArtistList) {
            String profileImgFilename = findArtistDto.getProfileImgFilename();
            String imagePath = fileDir + profileImgFilename;
            File file = new File(imagePath);
            String encodingImage = encodingService.encodingBase64(file);
            findArtistDto.setProfileImgFilename(encodingImage);
        }

        return findArtistList;
    }

    public FindArtistDto findOneArtist(Long findArtistId) throws IOException {
        User findArtist = userRepository.findById(findArtistId).orElseThrow(
                () -> new NoDataFoundException("해당하는 아티스트가 존재하지 않습니다.")
        );
        String profileImgFilename = findArtist.getProfileImgFilename();
        String imagePath = fileDir + profileImgFilename;
        File file = new File(imagePath);
        String encodingImage = encodingService.encodingBase64(file);

        FindArtistDto body = new FindArtistDto();
        body.setUserId(findArtist.getId());
        body.setNickname(findArtist.getNickname());
        body.setReceiveVote(findArtist.getReceiveVote());
        body.setProfileImgFilename(encodingImage);

        return body;
    }

    public Integer getRemainVote() {
        Long userId = getCurrentUserId();
        User user = userRepository.findById(userId).get();
        return user.getVote();
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

    public void vote(Long userId, Long voteUserId, Integer voteAmount) {
        User user = userRepository.findById(userId).get();
        int remainVote = user.getVote();
        if(remainVote - voteAmount < 0) throw new LackVoteAmountException("투표권의 갯수가 부족합니다.");

        User Artist = userRepository.findById(voteUserId).get();
        user.setVote(user.getVote()-voteAmount); // 유저의 투표권 수 감소
        Artist.setReceiveVote(Artist.getReceiveVote() + voteAmount); // 아티스트의 받은 투표수 증가
    }

    public Map<String, Object> ranking() throws IOException {
        List<MyPickRankingDto> myPickRanking = myPickQueryRepository.findMyPickRanking();
        for (MyPickRankingDto myPickRankingDto : myPickRanking) {
            String profileImgFilename = myPickRankingDto.getProfileImg();
            String imagePath = fileDir + profileImgFilename;
            File file = new File(imagePath);
            String encodingImage = encodingService.encodingBase64(file);
            myPickRankingDto.setProfileImg(encodingImage);
        }
        Map<String, Object> body = new HashMap<>();

        if(myPickRanking.get(0).getReceiveVote() == 0 ){
            body.put("1st", null);
            body.put("2st", null);
            body.put("3st", null);
            return body;
        }else if(myPickRanking.get(1).getReceiveVote().equals(0)) {
            body.put("1st", myPickRanking.get(0));
            body.put("2st", null);
            body.put("3st", null);
            return body;
        }else if(myPickRanking.get(2).getReceiveVote().equals(0)) {
            body.put("1st", myPickRanking.get(0));
            body.put("2st", myPickRanking.get(1));
            body.put("3st", null);
            return body;
        }
        body.put("1st", myPickRanking.get(0));
        body.put("2st", myPickRanking.get(1));
        body.put("3st", myPickRanking.get(2));
        return body;
    }

    public void createComment(String comment) {
        if(comment.isBlank()) throw new NullPointerException("댓글에 공백은 허용되지 않습니다.");
        Long userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoDataFoundException("user에 대한 정보가 없습니다.")
        );
        MypickComment myPickComment = new MypickComment();
        myPickComment.addMypcikComment(user, comment);
        mypickCommentRepository.save(myPickComment);
    }

    public List<MyPickCommentDto> getComment() {
        return myPickQueryRepository.getComment();
    }
}
