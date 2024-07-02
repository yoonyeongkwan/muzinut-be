package nuts.muzinut.controller.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.music.SongPageDto;
import nuts.muzinut.dto.page.PageDto;
import nuts.muzinut.service.music.SongService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/music")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping(value = "/newsong", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageDto<SongPageDto>> getNewSongs(Pageable pageable) {

        PageDto<SongPageDto> newSongs = songService.getNewSongs(pageable);

        return ResponseEntity.ok(newSongs);

    }
}
