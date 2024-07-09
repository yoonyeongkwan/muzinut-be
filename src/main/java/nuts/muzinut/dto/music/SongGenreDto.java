package nuts.muzinut.dto.music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.music.Genre;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongGenreDto {

    private Genre genre;
}
