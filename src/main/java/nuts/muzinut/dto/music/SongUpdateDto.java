package nuts.muzinut.dto.music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongUpdateDto {

    private String songName;
    private String lyricist;
    private String composer;
    private List<String> genres;
    private String lyrics;
}
