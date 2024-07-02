package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.User;

@Entity
@Table(name = "comment_like")
@Getter
@NoArgsConstructor
public class CommentLike {

    @Id
    @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    /**
     * 연관 관계 메서드
     * @param user: 좋아요를 누른 사용자
     * @param comment: 좋아요를 누른 댓글
     */
    public void addLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
        user.getCommentLikes().add(this);
        comment.getCommentLikes().add(this);
    }
}
