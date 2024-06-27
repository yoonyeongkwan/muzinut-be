package nuts.muzinut.dto.mainpage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArtistDto {

    private Long userId;
    private String profileImg;
    private String nickname;

}
