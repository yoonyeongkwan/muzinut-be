package nuts.muzinut.repository.mainpage;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import nuts.muzinut.dto.mainpage.HotArtistDto;
import nuts.muzinut.repository.music.AlbumRepository;
import nuts.muzinut.repository.music.PlayViewRepository;
import nuts.muzinut.repository.music.SongGenreRepository;
import nuts.muzinut.repository.music.SongRepository;
import nuts.muzinut.service.music.AlbumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
class MainPageRepositoryTest {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongGenreRepository songGenreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MainPageRepository mainPageRepository;

    @Autowired
    private PlayViewRepository playViewRepository;

    @Test
    void findTOP10Song() {
        mainPageRepository.findTOP10Song();
    }

    @Test
    void findTOP5Artist(){

        List<HotArtistDto> top5Artist = mainPageRepository.findTOP5Artist();

    }
}