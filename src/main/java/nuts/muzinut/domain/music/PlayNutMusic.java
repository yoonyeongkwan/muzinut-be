package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "playnut_music")
public class PlayNutMusic {

    @Id @GeneratedValue
    @Column(name = "playnut_music_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playnut_id")
    private PlayNut playNut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    //연관 관계 메서드
    public void addPlaylistMusic(PlayNut playlist, Song song) {
        this.playNut = playlist;
        this.song = song;
        playlist.getPlayNutMusics().add(this);
    }

    public PlayNutMusic(PlayNut playNut, Song song) {
        this.playNut = playNut;
        this.song = song;
    }
}
