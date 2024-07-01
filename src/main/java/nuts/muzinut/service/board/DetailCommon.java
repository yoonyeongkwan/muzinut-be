package nuts.muzinut.service.board;

import lombok.Data;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;

//모든 상세 게시판 페이지에서 공통으로 쓰는 기능
public class DetailCommon {
    public boolean isLike(User user, Board board) {
        for (Like l : board.getLikes()) {
            if (l.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public boolean isLike(User user, Comment comment) {
        for (CommentLike l : comment.getCommentLikes()) {
            if (l.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBookmark(User user, Board board) {
        for (Bookmark b : board.getBookmarks()) {
            if (b.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }
}
