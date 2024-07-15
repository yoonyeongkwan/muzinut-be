package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.PlayNutMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PlayNutMusicRepository extends JpaRepository<PlayNutMusic, Long>, PlayNutMusicRepositoryCustom {

    @Transactional
    @Modifying
    @Query("delete from PlayNutMusic pnm where pnm.playNut.id = :playNutId and pnm.id = :playNutMusicId")
    void deleteByPlayNutIdANDPlayNutMusicId(@Param("playNutId")Long playNutId, @Param("playNutMusicId")Long playNutMusicId);
}
