package nuts.muzinut.dto.mainpage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuts.muzinut.dto.page.PageDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchTotalDto {

    private PageDto<SearchArtistDto> searchArtistDtos;
    private PageDto<SearchSongDto> searchSongDtos;
}
