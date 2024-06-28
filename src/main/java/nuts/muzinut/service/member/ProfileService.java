package nuts.muzinut.service.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.ProfileDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.board.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final FollowService followService;
    private final ObjectMapper objectMapper;

    // 프로필 페이지 보여주는 메소드
    public ProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 팔로잉 수, 팔로워 수 가져오기
        Long followingCount = followService.countFollowing(user);
        Long followersCount = followService.countFollowers(userId);

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
                followersCount
        );

        return profileDto;
    }
}
