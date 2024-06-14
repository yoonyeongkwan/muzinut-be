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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "board_type")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    //연관 관계 메서드
    public void addBookmark(User user, Long boardId, BoardType boardType) {
        this.user = user;
        this.boardId = boardId;
        this.boardType = boardType;
        user.getBookmarks().add(this);
    }
}
