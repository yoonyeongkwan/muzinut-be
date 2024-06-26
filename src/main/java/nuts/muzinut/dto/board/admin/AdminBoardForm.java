package nuts.muzinut.dto.board.admin;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//어드민 게시판이 작성될 때 받을 데이터
@Data
public class AdminBoardForm {

    @NotNull(message = "제목은 반드시 필요합니다.")
    private String title;
}
