package nuts.muzinut.dto.music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongDetaillDto {
    private String albumImg;
    private String title;
    private String nickname;
    private Long likeCount;
    private String lyrics;
    private String composer;
    private String lyricist;
    private Long albumId;


}
