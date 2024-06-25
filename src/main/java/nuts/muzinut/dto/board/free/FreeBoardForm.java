package nuts.muzinut.dto.board.free;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FreeBoardForm {
    @NotNull
    private String title;
}
