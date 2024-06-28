package nuts.muzinut.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import nuts.muzinut.domain.member.User;

@Data
@AllArgsConstructor
public class ProfileDto {

    private String profileBannerImgName;
    private String profileImgName;
    private String nickname;
    private String intro;
    private Long followingCount;
    private Long followersCount;
}
