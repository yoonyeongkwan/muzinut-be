package nuts.muzinut.dto.mainpage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchArtistDto {

    private Long userId;
    private String profileImg;
    private String nickname;
    private Long followCount;
}
