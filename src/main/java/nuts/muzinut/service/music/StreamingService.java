package nuts.muzinut.service.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.repository.music.SongRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class StreamingService {
    private final SongRepository songRepository;
    @Value("${spring.file.dir}")
    private String fileDir;
    public UrlResource streamingSong(Long songId) {
        String songName = songRepository.findById(songId).get().getFileName();
        Path songPath = Paths.get(fileDir + "/songFile/" + songName);
        UrlResource resource;
        try {
            resource = new UrlResource(songPath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return resource;
    }
}
