package nuts.muzinut.dto.music;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Album;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDto {


    private String albumName;
    private String albumBio;
    private List<SongDto> songs;


}
