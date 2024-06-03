package nuts.muzinut.domain.music;

import jakarta.persistence.*;

@Entity
@Table(name = "playlist_music")
public class PlaylistMusic {

    @Id @GeneratedValue
    @Column(name = "playlist_music_id")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY, cascade =  CascadeType.ALL)
    private Playlist playlist;

    private Long music_id;

    //편의 메서드
    public void addRecord(Playlist playlist) {
        this.playlist = playlist;
        playlist.getPlaylistMusics().add(this);
    }
}
