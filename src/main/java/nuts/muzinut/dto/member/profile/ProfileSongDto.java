package nuts.muzinut.dto.member.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileSongDto {

    private String fileName;
    private String title;
    private String genre;
    private String intro;
    private int likeCount;
}
