package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Music extends BaseBoardEntity {

    @Id @GeneratedValue
    @Column(name = "music_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String intro;
    private String article;

    @Column(name = "music_origin_filename")
    private String musicOriginFilename;

    @Column(name = "music_store_filename")
    private String musicStoreFilename;

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL)
    private List<MusicGenre> musicGenres = new ArrayList<>();

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL)
    private List<MusicCorpArtist> musicCorpArtists = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    //연관 관계 편의 메서드
    public void createMusic(Member member) {
        this.member = member;
        member.getMusicList().add(this);
    }
}
