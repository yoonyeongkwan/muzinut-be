package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;

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

    //관계 매핑
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();
}
