package nuts.muzinut.dto.mypick;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPickCommentDto {
    private Long userId;
    private String nickname;
    private String comment;
    private String profileImg;
}
