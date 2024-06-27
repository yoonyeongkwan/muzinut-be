package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.RecruitBoardGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecruitBoardGenreRepository extends JpaRepository<RecruitBoardGenre, Long> {

    // 장르 존재 여부를 확인하는 메소드
    boolean existsByGenre(String genre);

    // 장르 삭제하는 메소드
    @Modifying
    @Query("DELETE FROM RecruitBoardGenre rbg WHERE rbg.recruitBoard.id = :recruitBoardId")
    void deleteByRecruitBoardId(@Param("recruitBoardId") Long recruitBoardId);
}
