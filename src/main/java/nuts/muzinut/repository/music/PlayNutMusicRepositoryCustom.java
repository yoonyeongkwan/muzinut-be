package nuts.muzinut.repository.music;

import nuts.muzinut.dto.music.PlayNutMusicDto;

import java.util.List;

public interface PlayNutMusicRepositoryCustom {

    List<PlayNutMusicDto> findPlayNutMusic(Long playNutId);
}
