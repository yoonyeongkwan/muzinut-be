package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.User;

@Entity
@Getter
public class Lounge extends Board {

    @Id @GeneratedValue
    @Column(name = "lounge_id")
    private Long id;

    private String filename; //react quill file
}
