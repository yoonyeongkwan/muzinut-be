package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.User;

@Entity
@Getter
public class Reply extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    //연관 관계 메서드
    public void addReply(Comment comment, String content, User user) {
        this.comment = comment;
        this.content = content;
        this.user = user;
        comment.getReplies().add(this);
    }

    // 대댓글 내용 수정 메서드
    public void modifyContent(String content) {
        this.content = content;
    }
}
