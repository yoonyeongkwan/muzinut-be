package nuts.muzinut.dto.music;

import lombok.Data;
import nuts.muzinut.domain.music.Genre;
import nuts.muzinut.domain.music.Song;

import java.util.List;

@Data
public class SongDto {


    private String songName;
    private String lyricist;
    private String composer;
    private List<String> genres;
    private String lyrics;
    private String musicFilename;

    public Song toEntity() {
        return new Song(this.songName, this.lyrics, this.composer, this.lyricist, this.musicFilename);
    }
}
