package nuts.muzinut.dto.member.follow;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FollowDto {
    @NotNull
    private Long userId;

    @NotNull
    private Long followingMemberId;
//
//    private String followingMemberUsername;
//    private String followingMemberNickname;
//    private String followingMemberProfileImgFilename;
}
