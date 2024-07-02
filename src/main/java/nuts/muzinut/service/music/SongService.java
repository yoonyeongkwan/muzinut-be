package nuts.muzinut.service.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.music.SongPageDto;
import nuts.muzinut.dto.page.PageDto;
import nuts.muzinut.repository.music.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SongService {

    private final SongRepository songRepository;

    public PageDto<SongPageDto> getNewSongs(Pageable pageable) {
        Page<SongPageDto> page = songRepository.new100Song(pageable);
        return new PageDto<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
    }
}
