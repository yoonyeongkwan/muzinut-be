package nuts.muzinut.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailRequestDto {

    @Email
    @NotEmpty(message = "인증 번호를 입력해주세요")
    private String code;
}
