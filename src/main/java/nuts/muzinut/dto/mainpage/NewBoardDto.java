package nuts.muzinut.dto.mainpage;

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
}
