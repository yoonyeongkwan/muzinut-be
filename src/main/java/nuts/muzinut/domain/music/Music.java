package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.Member;

@Entity
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
}
