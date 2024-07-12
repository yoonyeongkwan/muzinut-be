package nuts.muzinut.controller.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.service.music.SongLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/music/detail")
@RequiredArgsConstructor
public class SongLikeController {

    private final SongLikeService songLikeService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{id}/songlike")
    public ResponseEntity<String> uploadAlbum(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(songLikeService.getSongLike(id));
    }
}
