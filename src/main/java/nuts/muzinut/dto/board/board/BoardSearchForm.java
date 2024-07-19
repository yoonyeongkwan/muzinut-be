package nuts.muzinut.dto.board.board;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardSearchForm {

    @NotBlank
    private String title; //검색할 제목
}
