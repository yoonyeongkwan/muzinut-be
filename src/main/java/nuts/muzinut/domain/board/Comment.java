package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    //연관 관계 메서드

    /**
     * @param user: 댓글 작성자
     * @param board: 작성한 댓글에 해당하는 게시판
     * @param content: 댓글 내용
     */
    public void addComment(User user, Board board, String content) {
        this.user = user;
        this.content = content;
        this.board = board;
        board.getComments().add(this);
        if (user.getComments() == null){
            user.setComments(new ArrayList<>());
        }
        user.getComments().add(this);
    }

    //관계 매핑
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    // 댓글 내용 수정 메서드
    public void modifyContent(String content) {
        this.content = content;
    }
}
