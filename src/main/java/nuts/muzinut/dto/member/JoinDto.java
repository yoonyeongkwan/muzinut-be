package nuts.muzinut.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinDto {

    @Email(message = "이메일 형식으로 아이디를 입력해야 합니다.")
    @NotNull
    private String username;

    @NotNull(message = "비밀번호를 필수로 입력해야 합니다.")
    private String password;

    @NotNull(message = "인증 번호를 입력해주세요")
    private String authNum;
}
