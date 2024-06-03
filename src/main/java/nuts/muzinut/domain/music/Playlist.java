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

    //Todo
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "playlist")
    private List<PlaylistMusic> playlistMusics = new ArrayList<>();
}
