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

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Column(name = "song_id")
    private Long songId;

    //편의 메서드
    public void addRecord(Playlist playlist, Long songId) {
        this.playlist = playlist;
        this.songId = songId;
        playlist.getPlaylistMusics().add(this);
    }
}
