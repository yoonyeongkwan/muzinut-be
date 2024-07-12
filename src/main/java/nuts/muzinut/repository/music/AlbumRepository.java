package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Album;
import nuts.muzinut.dto.music.AlbumDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long>, AlbumRepositoryCustom {

    Optional<Album> findByName(String name);

    Optional<Album> findByAlbumImg(String filename);

    @Query("SELECT a FROM Album a WHERE a.user.id = :userId ORDER BY a.id DESC")
    List<Album> findAllByUserIdOrderByLatest(Long userId); // 마이페이지 - 앨범 리스트 최신순으로 가져오는 메소드 추가
    @Query("select a from Album a join a.user u where a.id = :albumId and u.id = :userId")
    Optional<Album> findByUser(@Param("albumId")Long albumId, @Param("userId")Long userId);

    @Modifying
    @Transactional
    @Query("update Album a set a.name = :name, a.intro = :intro, a.albumImg = :albumImg "
    + "where a.id = :id")
    void updateById(@Param("name") String name, @Param("intro") String intro,
                    @Param("albumImg") String albumImg, @Param("id") Long id);


}

