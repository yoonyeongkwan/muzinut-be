package nuts.muzinut.domain.music;

import jakarta.persistence.*;

@Entity
@Table(name = "playlist_music")
public class PlaylistMusic {

    @Id @GeneratedValue
    @Column(name = "playlist_music_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Column(name = "music_id")
    private Long musicId;

    //편의 메서드
    public void addPlaylistMusic(Playlist playlist) {
        this.playlist = playlist;
        playlist.getPlaylistMusics().add(this);
    }
}
