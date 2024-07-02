package nuts.muzinut.controller.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.music.PlaylistMusic;
import nuts.muzinut.service.music.PlaylistService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/get")
    public List<PlaylistMusic> getPlaylist() {
        return playlistService.getPlaylist();
    }
}
