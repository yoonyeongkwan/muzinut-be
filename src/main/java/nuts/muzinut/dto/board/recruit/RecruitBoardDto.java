package nuts.muzinut.dto.board.recruit;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecruitBoardDto {
    private List<RecruitBoardsForm> recruitBoardsForms = new ArrayList<>();
}
