package nuts.muzinut.dto.music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDetaillDto {

    private String name;
    private String albumImg;
    private String nickname;
    private String intro;
}
