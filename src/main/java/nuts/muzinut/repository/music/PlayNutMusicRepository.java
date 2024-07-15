package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.PlayNut;
import nuts.muzinut.domain.music.PlayNutMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayNutMusicRepository extends JpaRepository<PlayNutMusic, Long>, PlayNutMusicRepositoryCustom {
    List<PlayNutMusic> findAllByPlayNut(PlayNut playnut);
}
