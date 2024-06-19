package nuts.muzinut.dto.board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecruitBoardDto {
    private List<RecruitBoardsForm> recruitBoardsForms = new ArrayList<>();
}
