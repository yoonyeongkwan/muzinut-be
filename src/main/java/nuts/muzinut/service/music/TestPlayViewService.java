package nuts.muzinut.service.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.music.PlayView;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.repository.music.PlayViewRepository;
import nuts.muzinut.repository.music.SongRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestPlayViewService {
    private final PlayViewRepository playViewRepository;
    private final SongRepository songRepository;
    public void TestPlayView(Long id) {
        Optional<Song> byId = songRepository.findById(id);
        Song song = byId.get();
        PlayView playView = new PlayView(song);
        playViewRepository.save(playView);
    }
}
