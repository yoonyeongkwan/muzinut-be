package nuts.muzinut.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class SaveAdminBoardDto {

    private String title;
    private String content;
    private int view;
    private List<StoreFilename> storeFilenames;
}
