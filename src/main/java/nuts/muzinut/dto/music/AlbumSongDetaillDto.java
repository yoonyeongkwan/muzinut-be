package nuts.muzinut.dto.music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumSongDetaillDto {

    private Long songId;
    private String title;
    private String nickname;
}
