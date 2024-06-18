package nuts.muzinut.dto.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AdminBoardForm {

    private String title;
    private String content;
    private List<MultipartFile> attachFiles;
//    private MultipartFile attachFile;
}
