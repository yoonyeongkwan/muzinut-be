package nuts.muzinut.dto.board.admin;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//어드민 게시판이 작성될 때 받을 데이터
@Data
public class AdminBoardForm {

    private String title;
    private String content;
    private List<MultipartFile> attachFiles;
}
