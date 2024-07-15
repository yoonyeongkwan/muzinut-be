package nuts.muzinut.dto.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBoardDto {

    List<NewFreeBoardDto> freeBoardDtos;
    List<NewRecruitBoardDto> recruitBoardDtos;

    @JsonIgnore
    public boolean isEmpty() {
        return (freeBoardDtos == null || freeBoardDtos.isEmpty()) &&
                (recruitBoardDtos == null || recruitBoardDtos.isEmpty());
    }
}
