package nuts.muzinut.dto.board.admin;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//모든 어드민 게시판을 반환하는 dto
@Data
public class AdminBoardsDto {
    private List<AdminBoardsForm> adminBoardsForms = new ArrayList<>();
    private int currentPage;
    private int totalPage;
    private Long totalContent; //조회된 총 데이터 수

    public void setPaging(int currentPage, int totalPage, Long totalContent) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.totalContent = totalContent;
    }

    public void setPaging(int currentPage, int totalPage) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }
}
