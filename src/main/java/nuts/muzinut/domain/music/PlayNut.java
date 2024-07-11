package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class PlayNut {

    @Id @GeneratedValue
    @Column(name = "playnut_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "playNut", cascade = CascadeType.ALL)
    private List<PlayNutMusic> playNutMusics = new ArrayList<>();

    //연관관계 메서드
    public void createPleNut(User user) {
        this.user = user;
        user.getPlayNutList().add(this);
    }
}
