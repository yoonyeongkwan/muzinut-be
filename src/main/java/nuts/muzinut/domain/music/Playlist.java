package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Playlist {

    @Id @GeneratedValue
    @Column(name = "playlist_record")
    private Long id;

    @OneToOne(mappedBy = "playlist",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<PlaylistMusic> playlistMusics = new ArrayList<>();

    //연관 관계 메서드
    public void createPlaylist(User user) {
        this.user = user;
        user.setPlaylist(this);
    }
}
