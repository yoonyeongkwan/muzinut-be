package nuts.muzinut.controller.artist;


import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.artist.HotArtistDto;
import nuts.muzinut.dto.page.PageDto;
import nuts.muzinut.service.artist.ArtistService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/music")
public class ArtistCotroller {

    private final ArtistService artistService;

    @GetMapping(value = "/hot-artist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageDto<HotArtistDto>> hotArtist(@RequestParam(name = "page", defaultValue = "1")int pageable) throws IOException {
        PageDto<HotArtistDto> hotArtist = artistService.hotArtist(pageable);

        return ResponseEntity.ok()
                .body(hotArtist);
    }
}
