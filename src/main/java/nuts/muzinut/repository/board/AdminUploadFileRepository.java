package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.AdminUploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminUploadFileRepository extends JpaRepository<AdminUploadFile, Long> {

    @Query(value = "select a from AdminUploadFile a join fetch a.adminBoard " +
            "where a.adminBoard.id = :boardId")
    List<AdminUploadFile> getAdminUploadFile(@Param("boardId") Long boardId);

    @Modifying
    @Query(value = "delete from AdminUploadFile a where a.adminBoard = :adminBoard")
    void deleteByAdminBoard(@Param("adminBoard") AdminBoard adminBoard);

    @Modifying
    @Query("update AdminUploadFile a set a.storeFilename = :storeFilename, a.originFilename = :originFilename " +
            "where a.adminBoard = :adminBoard")
    void updateAttachedFile(@Param("storeFilename") String storeFilename, @Param("originFilename") String originFilename,
                         @Param("adminBoard") AdminBoard adminBoard);

}
