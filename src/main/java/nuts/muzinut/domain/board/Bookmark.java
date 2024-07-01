package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.User;

@Entity
@Getter
public class Bookmark {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    //연관 관계 메서드
    public void addBookmark(User user, Board board) {
        this.user = user;
        this.board = board;
        user.getBookmarks().add(this);
        board.getBookmarks().add(this);
    }
}
