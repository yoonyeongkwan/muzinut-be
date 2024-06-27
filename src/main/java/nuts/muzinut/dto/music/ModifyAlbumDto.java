package nuts.muzinut.dto.music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyAlbumDto {

    private Long albumId;
    private String albumName;
    private String albumBio;
    private List<ModifySongDto> songs;
}
