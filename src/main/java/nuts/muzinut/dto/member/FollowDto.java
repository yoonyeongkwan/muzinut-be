package nuts.muzinut.dto.member;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FollowDto {
    @NotNull
    private Long userId;

    @NotNull
    private Long followingMemberId;
}
