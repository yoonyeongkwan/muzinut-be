package nuts.muzinut.dto.music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {

    private String songName;
    private String lyricist;
    private String composer;
    private List<String> genres;
    private String lyrics;
    private String originFilename;

}
