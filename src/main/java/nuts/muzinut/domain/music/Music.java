package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
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

    @Column(name = "music_img_origin_filename")
    private String musicImgOriginFilename;

    @Column(name = "music_img_store_filename")
    private String musicImgStoreFilename;

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL)
    private List<MusicGenre> musicGenres = new ArrayList<>();

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL)
    private List<MusicCorpArtist> musicCorpArtists = new ArrayList<>();

    //연관 관계 편의 메서드
    public void addMusic(Member member) {
        this.member = member;
        member.getMusicList().add(this);
    }
}
