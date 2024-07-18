package nuts.muzinut.dto.mypick;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPickRankingDto {
    private Long userId;
    private String nickname;
    private Integer receiveVote;
    private String profileImg;
}
