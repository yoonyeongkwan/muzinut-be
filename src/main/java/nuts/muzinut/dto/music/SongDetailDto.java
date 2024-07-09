package nuts.muzinut.dto.music;

import com.querydsl.jpa.JPAExpressions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.music.Genre;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongDetailDto {
    private String title;
    private String lyrics;
    private String lyricist;
    private String composer;
    private String albumImg;
    private String artist;
}
