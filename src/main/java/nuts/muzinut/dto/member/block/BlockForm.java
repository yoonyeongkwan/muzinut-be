package nuts.muzinut.dto.member.block;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlockForm {
    @NotNull
    private String blockUserNickname; //차단할 사용자의 닉네임
}
