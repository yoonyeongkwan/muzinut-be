package nuts.muzinut.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetNewPasswordForm {

    @NotNull
    @Email
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String authNum;
}
