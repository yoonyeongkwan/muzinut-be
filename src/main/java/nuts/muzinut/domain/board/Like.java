package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.Member;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor
public class Like {

    @Id @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "board_type")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column(name = "member_id")
    private Long memberId;

    public Like(Long boardId, BoardType boardType, Long memberId) {
        this.boardId = boardId;
        this.boardType = boardType;
        this.memberId = memberId;
    }
}
