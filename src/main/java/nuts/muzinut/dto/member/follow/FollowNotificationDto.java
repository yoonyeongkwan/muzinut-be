package nuts.muzinut.dto.member.follow;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowNotificationDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long followingMemberId;

    @NotNull
    private Boolean isEnabled;

    @Builder
    public FollowNotificationDto(
            @JsonProperty("userId") Long userId,
            @JsonProperty("followingMemberId") Long followingMemberId,
            @JsonProperty("isEnabled") Boolean isEnabled) {
        this.userId = userId;
        this.followingMemberId = followingMemberId;
        this.isEnabled = isEnabled != null ? isEnabled : true;
    }
}
