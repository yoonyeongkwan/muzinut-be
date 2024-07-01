package nuts.muzinut.service.board;

import lombok.Data;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;

//모든 상세 게시판 페이지에서 공통으로 쓰는 기능
public class DetailCommon {

    /**
     * 게시판에 사용자가 좋아요를 눌렀는지 확인
     * @param user: 좋아요를 눌렀는지 확인하고 싶은 유저
     * @param board: 좋아요 대상이 되는 게시판
     */
    public boolean isLike(User user, Board board) {
        for (Like l : board.getLikes()) {
            if (l.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 댓글에 사용자가 좋아요를 눌렀는지 확인
     * @param user: 좋아요를 눌렀는지 확인하고 싶은 유저
     * @param comment: 좋아요 대상이 되는 댓글
     */
    public boolean isLike(User user, Comment comment) {
        for (CommentLike l : comment.getCommentLikes()) {
            if (l.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 사용자가 게시판을 북마크 했는지 여부를 파악하기 위한 메서드
     * @param user: 북마크를 했는지 확인하고 싶은 유저
     * @param board: 북마크 대상이 되는 게시판
     */
    public boolean isBookmark(User user, Board board) {
        for (Bookmark b : board.getBookmarks()) {
            if (b.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }
}
