package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.RecruitBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecruitBoardRepository extends JpaRepository<RecruitBoard, Long> {

    // 특정 제목에 부합하는 모집 게시판 검색
    @Query("SELECT rb FROM RecruitBoard rb LEFT JOIN FETCH rb.recruitBoardGenres WHERE rb.title LIKE %:title%")
    Page<RecruitBoard> findByTitleContaining(@Param("title") String title, Pageable pageable);

    // 최신 순서대로 모집 게시판 조회
    @Query("SELECT rb FROM RecruitBoard rb ORDER BY rb.createdDt DESC")
    Page<RecruitBoard> findAllByOrderByCreatedDateDesc(Pageable pageable);

    // 조회수 순서대로 모집 게시판 조회
    @Query("SELECT rb FROM RecruitBoard rb ORDER BY rb.view DESC")
    Page<RecruitBoard> findAllByOrderByViewDesc(Pageable pageable);

    // 모집 장르에 따라 게시판 조회
    @Query("SELECT rb FROM RecruitBoard rb JOIN FETCH rb.recruitBoardGenres rbg WHERE rbg.genre = :genre")
    Page<RecruitBoard> findAllByGenre(@Param("genre") String genre, Pageable pageable);

}
