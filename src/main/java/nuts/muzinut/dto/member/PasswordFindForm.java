package nuts.muzinut.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordFindForm {

    @Email
    @NotNull
    private String username;
}
