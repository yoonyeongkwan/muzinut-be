package nuts.muzinut.dto.board.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.AdminUploadFile;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.dto.board.comment.CommentDto;

import java.util.ArrayList;
import java.util.List;

//특정 어드민 게시판을 반환할때 사용하는 dto
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailAdminBoardDto {

    private List<CommentDto> comments = new ArrayList<>();
    private Long likeCount;

    private String title;
    private String content;
    private int view;
    private List<AdminFilename> adminFilenames = new ArrayList<>();

    public DetailAdminBoardDto(String title, String content, int view, List<AdminFilename> adminFilenames) {
        this.title = title;
        this.content = content;
        this.view = view;
        this.adminFilenames = adminFilenames;
    }

    public void setAdminBoard(AdminBoard adminBoard) {
        title = adminBoard.getTitle();
        content = adminBoard.getContent();
        view = adminBoard.getView();

        List<AdminUploadFile> adminUploadFiles = adminBoard.getAdminUploadFiles();
        List<AdminFilename> adminFilenames = new ArrayList<>();

        for (AdminUploadFile adminUploadFile : adminUploadFiles) {
            adminFilenames.add(new AdminFilename(adminUploadFile.getStoreFilename(),
                    adminUploadFile.getOriginFilename(),adminUploadFile.getId()));
        }

    }
}
