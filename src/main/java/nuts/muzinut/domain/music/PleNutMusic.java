package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "playlist_music")
public class PleNutMusic {

    @Id @GeneratedValue
    @Column(name = "playlist_music_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "playlist_id")
    private PleNut pleNut;

    @Column(name = "music_id")
    private Long musicId;

    //연관 관계 메서드
    public void addPlaylistMusic(PleNut playlist, Music music) {
        this.pleNut = playlist;
        this.musicId = music.getId();
        playlist.getPlenutMusics().add(this);
    }
}
