package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class PlayNut {

    @Id @GeneratedValue
    @Column(name = "playnut_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "playNut", cascade = CascadeType.ALL)
    private List<PlayNutMusic> playNutMusics = new ArrayList<>();

    //연관관계 메서드
    public void createPleNut(Member member) {
        this.member = member;
        member.getPlayNutList().add(this);
    }
}
