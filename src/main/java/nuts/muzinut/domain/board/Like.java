package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.User;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor
public class Like {

    @Id @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    /**
     * 연관 관계 메서드
     * @param user: 좋아요를 누른 사용자
     * @param board: 좋아요를 누른 게시판
     */
    public void addLike(User user, Board board) {
        this.user = user;
        this.board = board;
        user.getLikeList().add(this);
        board.getLikes().add(this);
    }
}
