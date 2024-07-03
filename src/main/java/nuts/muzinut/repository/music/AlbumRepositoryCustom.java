package nuts.muzinut.repository.music;

import nuts.muzinut.dto.music.AlbumDetaillDto;
import nuts.muzinut.dto.music.AlbumSongDetaillDto;

import java.util.List;

public interface AlbumRepositoryCustom {

    List<AlbumDetaillDto> albumDetail(Long id);

    List<AlbumSongDetaillDto> albumSongDetail(Long id);
}
