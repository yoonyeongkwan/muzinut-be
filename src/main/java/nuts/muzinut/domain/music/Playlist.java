package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Playlist {

    @Id @GeneratedValue
    @Column(name = "playlist_record")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<PlaylistMusic> playlistMusics = new ArrayList<>();

    //연관 관계 메서드
    public void createPlaylist(Member member) {
        this.member = member;
        member.setPlaylist(this);
    }
}
