package nuts.muzinut.repository.music;


import nuts.muzinut.dto.music.SongDetaillDto;
import nuts.muzinut.dto.music.SongGenreDto;
import nuts.muzinut.dto.music.SongPageDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SongRepositoryCustom {

    Page<SongPageDto> new100Song(Pageable pageable);

    Page<SongPageDto> hotTOP100Song(Pageable pageable);

    Page<SongPageDto> genreSong(String genre, Pageable pageable);

    List<SongDetaillDto> songDetail(Long id);

    List<SongGenreDto> songDetailGenre(Long id);

}
