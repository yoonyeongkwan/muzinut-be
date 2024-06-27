package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.RecruitBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecruitBoardRepository extends JpaRepository<RecruitBoard, Long> {

    @Query("SELECT rb FROM RecruitBoard rb LEFT JOIN FETCH rb.recruitBoardGenres WHERE rb.title LIKE %:title%")
    Page<RecruitBoard> findByTitleContaining(@Param("title") String title, Pageable pageable);

    @Query("SELECT rb FROM RecruitBoard rb LEFT JOIN FETCH rb.recruitBoardGenres")
    Page<RecruitBoard> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query("SELECT rb FROM RecruitBoard rb LEFT JOIN FETCH rb.recruitBoardGenres")
    Page<RecruitBoard> findAllByOrderByViewDesc(Pageable pageable);

    @Query("SELECT rb FROM RecruitBoard rb LEFT JOIN FETCH rb.recruitBoardGenres rbg WHERE rbg.genre = :genre")
    Page<RecruitBoard> findAllByGenre(@Param("genre") String genre, Pageable pageable);

}




