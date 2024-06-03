package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.Member;

@Entity
@Table(name = "free_board")
public class FreeBoard extends BaseBoardEntity {

    @Id @GeneratedValue
    @Column(name = "free_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String filename; //본문 내용이 담긴 html 파일 (react.quails)
}
