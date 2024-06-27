package nuts.muzinut.service.artist;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nuts.muzinut.dto.artist.HotArtistDto;
import nuts.muzinut.dto.page.PageDto;
import nuts.muzinut.repository.artist.ArtistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public PageDto<HotArtistDto> hotArtist(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber()-1, 10);
        Page<HotArtistDto> page = artistRepository.hotArtist(pageRequest);

        return new PageDto<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );
    }
}
