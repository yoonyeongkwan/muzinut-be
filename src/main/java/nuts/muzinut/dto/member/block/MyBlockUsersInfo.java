package nuts.muzinut.dto.member.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
public class MyBlockUsersInfo {
    private Long id; //차단한 사람의 pk
    private String nickname; //차단한 사람의 별명
    private LocalDateTime blockTime; //차단 시간
    private String profileImg; //프로필 이미지 명
}
