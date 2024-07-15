package nuts.muzinut.dto.member.profile.PlayNut;

import lombok.AllArgsConstructor;
import lombok.Data;
import nuts.muzinut.dto.member.profile.ProfileDto;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfilePlayNutSongDto {
    private ProfileDto profile;
    private List<ProfilePlayNutSongsForm> playNutSongs;
}
