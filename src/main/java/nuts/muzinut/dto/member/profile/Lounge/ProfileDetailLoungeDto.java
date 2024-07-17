package nuts.muzinut.dto.member.profile.Lounge;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import nuts.muzinut.dto.board.comment.CommentDto;
import nuts.muzinut.dto.member.profile.ProfileDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileDetailLoungeDto extends ProfileDto {

    private Long id;
    private String writer;
    private int commentSize;
    private String quillFilename;
    private int likeCount;

    private List<CommentDto> comments = new ArrayList<>();

    private Boolean boardLikeStatus = false;
    private Boolean isBookmark = false;

    @QueryProjection
    public ProfileDetailLoungeDto(Boolean boardLikeStatus, Boolean isBookmark, String profileBannerImgName, String profileImgName, String nickname, String intro, Long followingCount, Long followersCount, boolean followStatus) {
        super(profileBannerImgName, profileImgName, nickname, intro, followingCount, followersCount, followStatus);
        this.boardLikeStatus = boardLikeStatus;
        this.isBookmark = isBookmark;
    }

    // 기본 생성자
    public ProfileDetailLoungeDto(Long id, String writer, String quillFilename, String profileBannerImgName, String profileImgName, String nickname, String intro, Long followingCount, Long followersCount, boolean followStatus) {
        super(profileBannerImgName, profileImgName, nickname, intro, followingCount, followersCount, followStatus);
        this.id = id;
        this.writer = writer;
        this.quillFilename = quillFilename;
    }

    // comments 리스트를 설정 & 댓글 갯수 설정하는 메서드
    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
        this.commentSize = comments != null ? comments.size() : 0;
    }
}
