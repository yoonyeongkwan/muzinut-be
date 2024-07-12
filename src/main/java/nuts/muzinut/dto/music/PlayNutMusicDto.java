package nuts.muzinut.dto.music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayNutMusicDto {

    private Long PlayNutMusicId;
    private Long songId;
    private String albumImg;
    private String title;
    private String nickname;
}
