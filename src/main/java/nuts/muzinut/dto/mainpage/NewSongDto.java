package nuts.muzinut.dto.mainpage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSongDto {

    private Long songId;
    private String albumImg;
    private String title;
    private String nickname;
}
