package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.Member;

@Entity
@Getter
public class Bookmark {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member member;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "board_type")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    //연관 관계 메서드
    public void addBookmark(Member member, Long boardId, BoardType boardType) {
        this.member = member;
        this.boardId = boardId;
        this.boardType = boardType;
        member.getBookmarks().add(this);
    }
}
