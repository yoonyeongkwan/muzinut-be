package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "board_id")
    private Long boardId; //music, recruitBoard .. 's pk

    @Column(name = "board_type")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    @OneToMany(mappedBy = "comment")
    private List<Reply> replies = new ArrayList<>();

    //연관 관계 메서드

    /**
     * @param member: 댓글 작성자
     * @param boardId: 작성한 댓글에 해당하는 게시판
     * @param boardType: 게시판의 종류 ex) 팀모집, 자유, 음원
     * @param content: 댓글 내용
     */
    public void addComment(Member member, Long boardId, BoardType boardType, String content) {
        this.member = member;
        this.boardId = boardId;
        this.boardType = boardType;
        this.content = content;
        member.getComments().add(this);
    }
}
