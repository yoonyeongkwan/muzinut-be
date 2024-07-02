package nuts.muzinut.dto.board.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuts.muzinut.dto.board.comment.CommentDto;

import java.util.ArrayList;
import java.util.List;

//특정 어드민 게시판을 반환할때 사용하는 dto
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailEventBoardDto {

    private Long id;
    private String title;
    private String writer = "muzi";
    private int view;
    private String quillFilename;
    private String img;
    private String profileImg;
    private Long likeCount;

    public DetailEventBoardDto(String title, String writer, int view) {
        this.title = title;
        this.writer = writer;
        this.view = view;
    }

    public DetailEventBoardDto(Long id , String title, String writer, int view, String quillFilename, String img, String profileImg) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.view = view;
        this.quillFilename = quillFilename;
        this.profileImg = profileImg;
    }
}
