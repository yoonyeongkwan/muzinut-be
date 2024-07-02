package nuts.muzinut.controller.artist;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.*;
import nuts.muzinut.dto.artist.HotArtistDto;
import nuts.muzinut.dto.page.PageDto;
import nuts.muzinut.repository.member.FollowRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.artist.ArtistService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
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
    public ResponseEntity<MultiValueMap<String, Object>> hotArtist(@RequestParam(name = "page", defaultValue = "1")int pageable) throws IOException {
        MultiValueMap<String, Object> hotArtist = artistService.hotArtist(pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_MIXED);

        return new ResponseEntity<>(hotArtist, headers, HttpStatus.OK);
    }
}
