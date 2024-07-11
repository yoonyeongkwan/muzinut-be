package nuts.muzinut.controller.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.music.SongPageDto;
import nuts.muzinut.dto.page.PageDto;
import nuts.muzinut.service.music.SongService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/music")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping(value = "/newsong", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> getNewSongs(@RequestParam(name = "page", defaultValue = "1")int pageable) {

        MultiValueMap<String, Object> data = songService.getNewSongs(pageable);

        return new ResponseEntity<MultiValueMap<String, Object>>(data, HttpStatus.OK);

    }
    @GetMapping(value = "/hotsong", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> getTOP10Songs(@RequestParam(name = "page", defaultValue = "1")int pageable) {

        MultiValueMap<String, Object> data = songService.getHotTOP100Songs(pageable);

        return new ResponseEntity<MultiValueMap<String, Object>>(data, HttpStatus.OK);

    }

}
