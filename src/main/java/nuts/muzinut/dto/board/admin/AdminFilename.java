package nuts.muzinut.dto.board.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

////특정 어드민 게시판을 반환할때 사용하는 form
@Data
@AllArgsConstructor
public class AdminFilename {
    private String storeFilename;
    private String originFilename;
    private Long id;
}
