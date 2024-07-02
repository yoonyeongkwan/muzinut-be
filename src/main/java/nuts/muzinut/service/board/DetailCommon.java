package nuts.muzinut.service.board;

import lombok.Data;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.board.comment.CommentDto;
import nuts.muzinut.dto.board.comment.ReplyDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<CommentDto> setCommentsAndReplies(User user, Board board) {
        List<CommentDto> comments = new ArrayList<>();

        for (Comment c : board.getComments()) {
            CommentDto commentDto;
            if (user != null) {
                // 회원인 경우
                commentDto = new CommentDto(
                        c.getId(), c.getContent(), c.getUser().getNickname(),
                        c.getCreatedDt(), c.getUser().getProfileImgFilename(),
                        isLike(user, c), c.getCommentLikes().size()); //isLike 추가 (댓글에 대한 좋아요를 했는지 확인)
            } else {
                // 비회원인 경우
                commentDto = new CommentDto(
                        c.getId(), c.getContent(), c.getUser().getNickname(),
                        c.getCreatedDt(), c.getUser().getProfileImgFilename(),
                        c.getCommentLikes().size());
            }

            List<ReplyDto> replies = new ArrayList<>();
            for (Reply r : c.getReplies()) {
                replies.add(new ReplyDto(
                        r.getId(), r.getContent(), r.getUser().getNickname(),
                        r.getCreatedDt(), r.getUser().getProfileImgFilename()));
            }
            commentDto.setReplies(replies);
            comments.add(commentDto);
        }
        return comments;
    }

    public Set<String> getProfileImages(String profileImg, List<CommentDto> comments) {

        Set<String> profileImages = new HashSet<>();
        addWriterProfile(profileImages, profileImg); //게시판 작성자의 프로필 추가

        for (CommentDto c : comments) {
            addWriterProfile(profileImages, c.getCommentProfileImg()); //댓글 작성자의 프로필 추가

            for (ReplyDto r : c.getReplies()) {
                addWriterProfile(profileImages, r.getReplyProfileImg()); //대댓글 작성자의 프로필 추가
            }
        }
        return profileImages;
    }

    private void addWriterProfile(Set<String> profileImages, String profileImg) {
        if (StringUtils.hasText(profileImg)) {
            profileImages.add(profileImg);
        }
    }

}
