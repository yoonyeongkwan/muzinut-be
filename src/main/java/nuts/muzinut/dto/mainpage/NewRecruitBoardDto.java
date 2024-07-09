package nuts.muzinut.dto.mainpage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewRecruitBoardDto {

    private Long recruitBoardId;
    private String title;
    private String nickname;
}
