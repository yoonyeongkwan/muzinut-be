package nuts.muzinut.dto.member.profile.Lounge;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProfileLoungesForm {
    private Long id;
    private String writer;
    private String filename;
    private LocalDateTime createdDt;
    private int like;
    private int view;
}
