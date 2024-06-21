package nuts.muzinut.dto.music;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.music.Album;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDto {

    private String albumImg;
    private String albumName;
    private String intro;
    private List<SongDto> songs;

    public Album toEntity() {
        return new Album(this.albumName, this.intro, this.albumImg);
    }
}
