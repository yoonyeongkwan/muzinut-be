package nuts.muzinut.dto.board.board;

import nuts.muzinut.dto.board.admin.AdminBoardsForm;

import java.util.ArrayList;
import java.util.List;

public class BoardsDto {
    private List<BoardsForm> boardsForms = new ArrayList<>();
    private int currentPage;
    private int totalPage;

    public void setPaging(int currentPage, int totalPage) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }
}
