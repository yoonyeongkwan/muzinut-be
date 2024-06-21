package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Song extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "song_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String name; // 곡 이름
    private String lyrics; // 가사

    private String composer; // 작곡가
    private String lyricist; // 작사가



    @Column(name = "music_store_filename")
    private String musicFilename;

    public Song(String name, String lyrics, String composer, String lyricist, String musicFilename) {
        this.name = name;
        this.lyrics = lyrics;
        this.composer = composer;
        this.lyricist = lyricist;
        this.musicFilename = musicFilename;
    }

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private List<SongGenre> songGenres = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    //연관 관계 편의 메서드
    public void createMusic(User user) {
        this.user = user;
        user.getSongList().add(this);
    }
}
