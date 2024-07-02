package nuts.muzinut.repository.member;

import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    Optional<User> findByNickname(String nickname); //nickname 으로 한명의 회원 조회

    Optional<User> findByUsername(String username); //username(email) 으로 회원 조회

    List<User> findAllByNickname(String nickname); //nickname 으로 검색 (아티스트 명을 검색할 때 활용)

    // 프로필 이미지 설정
    @Modifying
    @Query("update User u set u.profileImgFilename = :filename where u = :user")
    void updateFilename(@Param("filename") String filename, @Param("user") User user);

    // 프로필 닉네임, 자기소개 설정
    @Modifying
    @Query("update User u set u.nickname = :nickname, u.intro = :intro where u.id = :userId")
    void updateNicknameAndIntro(@Param("userId") Long userId, @Param("nickname") String nickname, @Param("intro") String intro);

    // 프로필 배너 이미지 설정
    @Modifying
    @Query("update User u set u.profileBannerImgFilename = :bannerFilename where u = :user")
    void updateProfileBannerImg(@Param("bannerFilename") String bannerFilename, @Param("user") User user);

    // 유저가 작성한 게시글 제목 조회
    @Query("select b.title from Board b where b.user.id = :userId")
    List<String> findBoardTitlesByUserId(@Param("userId") Long userId);
}