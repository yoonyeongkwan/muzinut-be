package nuts.muzinut.dto.board.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import nuts.muzinut.dto.board.comment.CommentDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class DetailBoardDto {
    private int likeCount;
    private String title;
    private String writer;
    private String quillFilename;
    private String profileImg; //저장된 게시판 작성자의 프로필 이미지 경로
    private int view;
    private Boolean boardLikeStatus = false;
    private Boolean isBookmark = false;
    private List<CommentDto> comments = new ArrayList<>();

    @QueryProjection
    public DetailBoardDto(int likeCount, String title, String writer, String quillFilename, String profileImg, int view, Boolean boardLikeStatus, Boolean isBookmark) {
        this.likeCount = likeCount;
        this.title = title;
        this.writer = writer;
        this.quillFilename = quillFilename;
        this.profileImg = profileImg;
        this.view = view;
        this.boardLikeStatus = boardLikeStatus;
        this.isBookmark = isBookmark;
    }
}
