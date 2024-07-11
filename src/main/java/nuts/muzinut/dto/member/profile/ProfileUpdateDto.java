package nuts.muzinut.dto.member.profile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileUpdateDto {

    @NotNull
    @Size(min=1, max = 10)
    private String nickname; //닉네임은 10글자를 넘어가서는 안된다.

    @NotNull
    private String intro;
}
