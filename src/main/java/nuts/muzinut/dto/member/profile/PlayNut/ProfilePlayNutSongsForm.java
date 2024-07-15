package nuts.muzinut.dto.member.profile.PlayNut;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfilePlayNutSongsForm {
    private String album;
    private String artist;
    private String songName;
//
//    // 기본 생성자
//    public ProfilePlayNutSongDto(String profileBannerImgName, String profileImgName, String nickname, String intro, Long followingCount, Long followersCount, boolean followStatus, String album, String artist, String songName) {
//        super(profileBannerImgName, profileImgName, nickname, intro, followingCount, followersCount, followStatus);
//        this.album = album;
//        this.artist = artist;
//        this.songName = songName;
//    }
//
//    // 새로운 생성자: 플리넛 없을 경우 프로필 정보만 반환
//    public ProfilePlayNutSongDto(String profileBannerImgName, String profileImgName, String nickname, String intro, Long followingCount, Long followersCount, boolean followStatus) {
//        super(profileBannerImgName, profileImgName, nickname, intro, followingCount, followersCount, followStatus);
//    }
}
