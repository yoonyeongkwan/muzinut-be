package nuts.muzinut.dto.board.lounge;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LoungesForm {
    private Long id;
    private String writer;
    private String filename;
    private LocalDateTime createdDt;
    private int like;
    private int commentSize;  // commentSize 필드 추가
}
