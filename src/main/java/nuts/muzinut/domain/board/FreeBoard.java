package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.User;

@Entity
@Getter
@Table(name = "free_board")
public class FreeBoard extends BaseBoardEntity {

    @Id @GeneratedValue
    @Column(name = "free_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String filename; //본문 내용이 담긴 html 파일 (react.quails)

    //연관 관계 메서드
    public void createFreeBoard(User user) {
        this.user = user;
        user.getFreeBoards().add(this);
    }
}
