package nuts.muzinut.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NicknameCheckDupForm {
    @NotBlank
    private String nickname;
}
