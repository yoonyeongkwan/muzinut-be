package nuts.muzinut.dto.board.admin;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//모든 어드민 게시판을 반환하는 dto
@Data
public class AdminBoardsDto {
    private List<AdminBoardsForm> adminBoardsForms = new ArrayList<>();
}
