package nuts.muzinut.controller.music;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.service.music.StreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/streaming")
@RequiredArgsConstructor
public class StreamingController {
    @Autowired
    StreamingService streamingService;
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{songId}")
    public ResponseEntity<Resource> streamingMusic(
            @Validated @PathVariable("songId") @NotNull Long songId) {
        UrlResource resource = streamingService.streamingSong(songId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", resource.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
