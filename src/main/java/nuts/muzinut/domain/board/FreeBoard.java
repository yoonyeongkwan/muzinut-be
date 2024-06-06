package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "free_board")
public class FreeBoard extends BaseBoardEntity {

    @Id @GeneratedValue
    @Column(name = "free_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String filename; //본문 내용이 담긴 html 파일 (react.quails)

    //연관 관계 메서드
    public void createFreeBoard(Member member) {
        this.setCreated_dt(LocalDateTime.now());
        this.setModified_dt(LocalDateTime.now());
        this.member = member;
        member.getFreeBoards().add(this);
    }
}
