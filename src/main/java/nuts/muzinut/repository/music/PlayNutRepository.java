package nuts.muzinut.repository.music;

import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.PlayNut;
import nuts.muzinut.domain.music.PlayNutMusic;
import nuts.muzinut.dto.music.PlayNutDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlayNutRepository extends JpaRepository<PlayNut, Long> {

    @Modifying
    @Transactional
    @Query("update PlayNut pn set pn.title = :title where pn.id = :id")
    void updateByTitle(@Param("title") String title,@Param("id") Long playNutId);

    @Query("select new nuts.muzinut.dto.music.PlayNutDto(pn.id, pn.title) from PlayNut pn where pn.user = :user")
    List<PlayNutDto> findByPlayNut(@Param("user")User user);

    @Query("SELECT pn FROM PlayNut pn WHERE pn.user.id = :userId")
    List<PlayNut> findByUserId(@Param("userId") Long userId);
}
