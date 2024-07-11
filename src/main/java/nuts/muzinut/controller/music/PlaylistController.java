package nuts.muzinut.controller.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.music.playlist.PlaylistAddDto;
import nuts.muzinut.dto.music.playlist.PlaylistDeleteDto;
import nuts.muzinut.dto.music.playlist.PlaylistMusicsDto;
import nuts.muzinut.service.music.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/get")
    public ResponseEntity<Map<String, List<PlaylistMusicsDto>>> getPlaylist() throws IOException {
        List<PlaylistMusicsDto> playlistMusics = playlistService.getPlaylistMusics();
        Map<String, List<PlaylistMusicsDto>> body = new HashMap<>();
        body.put("data", playlistMusics);

        return ResponseEntity.ok()
                .body(body);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/add")
    public ResponseEntity<String> addPlaylistMusic(@RequestBody PlaylistAddDto addListBody) {
        List<Long> addList = addListBody.getAddList();
        // 해당 음원 Entity 가 존재하는지 확인 -> 음원이 존재하지 않으면 exception 발생
        playlistService.addSongIsExist(addList);
        playlistService.addPlaylistMusics(addList); // 음원 추가

        return ResponseEntity.ok()
                .body("add success");
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlaylistMusic(@RequestBody PlaylistDeleteDto deleteListBody) {
        List<Long> deleteList = deleteListBody.getDeleteList();
        // 삭제하려는 PlaylistMusic Entity 가 존재하는지 Id 값으로 확인 -> 해당 Entity 가 존재하지 않으면 exception 발생
        playlistService.deleteSongIsExist(deleteList);
        playlistService.deletePlaylistMusics(deleteList); // 플레이리스트에서 음원 삭제

        return ResponseEntity.ok()
                .body("delete success");
    }
}
