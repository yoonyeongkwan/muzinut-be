package nuts.muzinut.dto.music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDetaillResultDto {

    private String name;
    private String albumImg;
    private String nickname;
    private String intro;
    private List<AlbumSongDetaillDto> songs;
}
