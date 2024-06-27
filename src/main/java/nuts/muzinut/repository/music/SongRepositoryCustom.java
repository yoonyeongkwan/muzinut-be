package nuts.muzinut.repository.music;


import nuts.muzinut.domain.music.Genre;
import nuts.muzinut.dto.music.SongPageDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SongRepositoryCustom {

    Page<SongPageDto> new100Song(Pageable pageable);

    Page<SongPageDto> hotTOP100Song(Pageable pageable);

    Page<SongPageDto> genreSong(String genre, Pageable pageable);
}
