package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "playnut_music")
public class PlayNutMusic {

    @Id @GeneratedValue
    @Column(name = "playnut_music_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playnut_id")
    private PlayNut playNut;

    @Column(name = "music_id")
    private Long musicId;

    //연관 관계 메서드
    public void addPlaylistMusic(PlayNut playlist, Song song) {
        this.playNut = playlist;
        this.musicId = song.getId();
        playlist.getPlayNutMusics().add(this);
    }
}
