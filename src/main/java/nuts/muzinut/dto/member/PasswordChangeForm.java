package nuts.muzinut.dto.member;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordChangeForm {

    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;
}
