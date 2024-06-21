package nuts.muzinut.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nuts.muzinut.domain.member.User;

@Data
@Builder
@AllArgsConstructor
public class FindNickNameDTO {

    private Long userId;
    private String nickname;


}
