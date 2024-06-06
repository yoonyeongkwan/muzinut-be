package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "playlist_music")
public class PlaylistMusic {

    @Id @GeneratedValue
    @Column(name = "playlist_music_id")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY, cascade =  CascadeType.ALL)
    private Playlist playlist;

    @Column(name = "music_id")
    private Long musicId;

    //편의 메서드
    public void addRecord(Playlist playlist, Music music) {
        this.playlist = playlist;
        this.musicId = music.getId();
        playlist.getPlaylistMusics().add(this);
    }
}
