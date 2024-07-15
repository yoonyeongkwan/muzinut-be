package nuts.muzinut.dto.mainpage;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.page.PageDto;

import java.util.List;

@Data
public class TapSearchDto<T> {
    private PageDto<T> totalData;

    public TapSearchDto(PageDto<T> totalData) {
        this.totalData = totalData;
    }
}
