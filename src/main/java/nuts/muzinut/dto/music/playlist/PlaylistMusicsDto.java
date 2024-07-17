package nuts.muzinut.dto.music.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistMusicsDto {
    private Long playlistId;
    private Long songId;
    private String title;
    private String artist;
    private String albumImg;
}
