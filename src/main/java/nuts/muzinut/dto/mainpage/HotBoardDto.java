package nuts.muzinut.dto.mainpage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotBoardDto {

    private Long boardId;
    private String title;
    private String nickname;
    private int view;
    private String dtype;
}
