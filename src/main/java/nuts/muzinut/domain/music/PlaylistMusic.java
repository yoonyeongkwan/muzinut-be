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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    //편의 메서드
    public void addRecord(Playlist playlist, Song song) {
        this.playlist = playlist;
        this.song = song;
        playlist.getPlaylistMusics().add(this);
        song.getPlaylistMusics().add(this);
    }
}
