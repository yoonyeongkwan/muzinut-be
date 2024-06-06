package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.Member;

@Entity
@Getter
@Table(name = "music_corp_artist")
public class MusicCorpArtist {

    @Id @GeneratedValue
    @Column(name = "music_corp_artist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Music music;

    @Column(name = "member_id")
    private Long memberId;

    public void addCorpArtist(Music music, Member member) {
        this.music = music;
        this.memberId = member.getId();
        music.getMusicCorpArtists().add(this);
    }
}
