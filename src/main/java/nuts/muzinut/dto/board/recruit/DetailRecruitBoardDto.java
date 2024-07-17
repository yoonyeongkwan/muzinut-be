package nuts.muzinut.dto.board.recruit;

import lombok.AllArgsConstructor;
import lombok.Data;
import nuts.muzinut.dto.board.DetailBaseDto;
import nuts.muzinut.dto.board.comment.CommentDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DetailRecruitBoardDto extends DetailBaseDto {

    private String title;
    private String content;
    private int view;
    private int recruitMember;
    private LocalDateTime startDuration;
    private LocalDateTime endDuration;
    private LocalDateTime startWorkDuration;
    private LocalDateTime endWorkDuration;
    private List<String> genres;
    private String author; // 작성자 정보 추가
    private long likeCount;  // 좋아요 수 추가
    private String profileImgFilename; // 프로필 이미지 파일명 추가
    private List<CommentDto> comments = new ArrayList<>(); // 댓글 리스트 추가

    public DetailRecruitBoardDto(String title, String content, int view, int recruitMember, LocalDateTime startDuration, LocalDateTime endDuration, LocalDateTime startWorkDuration, LocalDateTime endWorkDuration, List<String> genres, String author, String profileImgFilename) {
        this.title = title;
        this.content = content;
        this.view = view;
        this.recruitMember = recruitMember;
        this.startDuration = startDuration;
        this.endDuration = endDuration;
        this.startWorkDuration = startWorkDuration;
        this.endWorkDuration = endWorkDuration;
        this.genres = genres;
        this.author = author;
        this.profileImgFilename = profileImgFilename;
    }
}