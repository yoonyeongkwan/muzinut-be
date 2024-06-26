package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Board extends BaseBoardEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    //관계 매핑
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    //연관 관계 메서드
    public void addBoard(User user) {
        this.user = user;
        user.getBoards().add(this);
    }
}
