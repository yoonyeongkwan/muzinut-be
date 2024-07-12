package nuts.muzinut.dto.member.block;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyBlocksDto {
    private List<MyBlockUsersInfo> blockUsersInfo = new ArrayList<>();
}
