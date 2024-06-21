package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Album {

    @Id @GeneratedValue
    @Column(name = "album_id")
    private Long id;

    private String name; // 앨범 이름
    private String intro; // 앨범 소개

    @Column(name = "album_img")
    private String albumImg;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Album(String name, String intro, String albumImg) {
        this.name = name;
        this.intro = intro;
        this.albumImg = albumImg;
    }

    @OneToMany(mappedBy = "album")
    private List<Song> songList = new ArrayList<>();



    //==연관관계 메서드==//
    public void addSongIntoAlbum(Song song) {
        this.songList.add(song);
        song.setAlbum(this);
    }

    // 생성 메서드

}
