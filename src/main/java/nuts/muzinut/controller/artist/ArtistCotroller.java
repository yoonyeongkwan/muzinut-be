package nuts.muzinut.controller.artist;


import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.artist.HotArtistDto;
import nuts.muzinut.dto.page.PageDto;
import nuts.muzinut.service.artist.ArtistService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/music")
public class ArtistCotroller {

    private final ArtistService artistService;

    @GetMapping(value = "/hot-artist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageDto<HotArtistDto>> hotArtist(Pageable pageable) {
        PageDto<HotArtistDto> hotArtist = artistService.hotArtist(pageable);

        return ResponseEntity.ok(hotArtist);
    }
}
