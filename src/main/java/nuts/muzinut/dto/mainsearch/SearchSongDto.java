package nuts.muzinut.dto.mainsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSongDto {

    private Long songId;
    private String albumImg;
    private String title;
    private String nickname;
}
