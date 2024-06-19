package nuts.muzinut.dto.board;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminBoardsDto {
    private List<AdminBoardsForm> adminBoardsForms = new ArrayList<>();
}
