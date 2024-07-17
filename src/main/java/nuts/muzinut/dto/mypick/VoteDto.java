package nuts.muzinut.dto.mypick;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private Long voteUserId;
    private Integer voteAmount;
}
