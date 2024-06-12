package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Album {

    @Id @GeneratedValue
    @Column(name = "album_id")
    private Long id;

    private String name;
    private String intro;

    @Column(name = "album_img")
    private String albumImg;

    @OneToMany(mappedBy = "album")
    private List<Song> songList = new ArrayList<>();

    //연관 관계 메서드
    public void addSongIntoAlbum(Song song) {
        this.songList.add(song);
        song.setAlbum(this);
    }
}
