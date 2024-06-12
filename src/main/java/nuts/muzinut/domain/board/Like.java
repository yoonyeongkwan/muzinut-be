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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * @param boardId: 좋아요를 누른 게시판 pk
     * @param boardType: 좋아요를 누른 게시판 타입 (팀 모집, 음악...)
     * @param member: 좋아요를 누른 회원
     */
    public Like(Long boardId, BoardType boardType, Member member) {
        this.boardId = boardId;
        this.boardType = boardType;
        this.member = member;
        member.getLikeList().add(this);
    }
}
