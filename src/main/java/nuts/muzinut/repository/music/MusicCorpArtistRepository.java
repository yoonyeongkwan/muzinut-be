package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.SongCorpArtist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicCorpArtistRepository extends JpaRepository<SongCorpArtist, Long> {
}
