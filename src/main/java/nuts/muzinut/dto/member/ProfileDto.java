package nuts.muzinut.dto.member;

import lombok.Data;
import nuts.muzinut.domain.member.User;

@Data
public class ProfileDto {
    private User user;
    private int followerCount;
    private int followingCount;
}
