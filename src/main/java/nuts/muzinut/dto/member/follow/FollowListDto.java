package nuts.muzinut.dto.member.follow;

import lombok.Getter;
import lombok.Setter;
import nuts.muzinut.domain.member.User;

@Getter
@Setter
public class FollowListDto {

    private Long id;
    private String username;
    private String nickname;
    private String profileImgFilename;

    // Long id를 받는 생성자 추가
    public FollowListDto(Long id) {
        this.id = id;
    }

    // User 객체를 받는 생성자 추가
    public FollowListDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.profileImgFilename = user.getProfileImgFilename();
    }
}
