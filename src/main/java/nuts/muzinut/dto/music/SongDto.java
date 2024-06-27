package nuts.muzinut.dto.music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Genre;
import nuts.muzinut.domain.music.Song;

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
