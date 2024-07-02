package nuts.muzinut.dto.mainpage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainTotalDto {

    private List<HotSongDto> top10Songs;
    private List<NewSongDto> newSongs;
    private List<HotArtistDto> top5Artists;
    private List<HotBoardDto> hotBoards;
    private NewBoardDto newBoard;

}
