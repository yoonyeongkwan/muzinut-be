package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Song extends BaseBoardEntity {

    @Id @GeneratedValue
    @Column(name = "song_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String intro;
    private String article;

    @Column(name = "music_origin_filename")
    private String musicOriginFilename;

    @Column(name = "music_store_filename")
    private String musicStoreFilename;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private List<SongGenre> songGenres = new ArrayList<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private List<SongCorpArtist> songCorpArtists = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    //연관 관계 편의 메서드
    public void createMusic(User user) {
        this.user = user;
        user.getSongList().add(this);
    }
}
