package nuts.muzinut.dto.music.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDeleteDto {
    private List<Long> deleteList;
}
