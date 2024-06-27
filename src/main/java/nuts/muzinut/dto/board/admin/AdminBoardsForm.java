package nuts.muzinut.dto.board.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

//모든 어드민 게시판을 반환하는 form
@Data
@AllArgsConstructor
public class AdminBoardsForm {
    private Long id;
    private String title;
    private String writer;
    private int view;
    private int like;
    private LocalDateTime createdDt;
}
