package nuts.muzinut.dto.board.free;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.AdminUploadFile;
import nuts.muzinut.dto.board.admin.AdminFilename;
import nuts.muzinut.dto.board.comment.CommentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

//특정 어드민 게시판을 반환할때 사용하는 dto
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailFreeBoardDto {

    private String title;
    private String writer = "muzi";
    private int view;
    private String quillFilename;

    private List<CommentDto> comments = new ArrayList<>();
    private Long likeCount;

    public DetailFreeBoardDto(String title, String writer, int view) {
        this.title = title;
        this.writer = writer;
        this.view = view;
    }

    public DetailFreeBoardDto(String title, String writer, int view, String quillFilename) {
        this.title = title;
        this.writer = writer;
        this.view = view;
        this.quillFilename = quillFilename;
    }


}
