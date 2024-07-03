package nuts.muzinut.dto.member.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.music.Album;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileSongDto {

    private String title;
    private String genre;
    private String intro;
    private int likeCount;
    private String albumImg; // 앨범 이미지 추가
}
