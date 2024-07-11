package nuts.muzinut.repository.music;

import jakarta.persistence.Column;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.music.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long>, AlbumRepositoryCustom {

    Optional<Album> findByName(String name);

    Optional<Album> findByAlbumImg(String filename);

    @Query("select a from Album a join a.user u where a.id = :albumId and u.id = :userId")
    Optional<Album> findByUser(@Param("albumId")Long albumId, @Param("userId")Long userId);

    @Modifying
    @Transactional
    @Query("update Album a set a.name = :name, a.intro = :intro, a.albumImg = :albumImg "
    + "where a.id = :id")
    void updateById(@Param("name") String name, @Param("intro") String intro,
                    @Param("albumImg") String albumImg, @Param("id") Long id);


}
