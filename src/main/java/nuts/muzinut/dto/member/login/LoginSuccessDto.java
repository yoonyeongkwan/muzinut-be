package nuts.muzinut.dto.member.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessDto {
    private String token;
    private String refreshToken;
    private String profileImg;
    private String nickname;
}
