package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.BoardType;
import nuts.muzinut.dto.board.admin.DetailAdminBoardDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminBoardRepository extends JpaRepository<AdminBoard, Long> {

//    @Query("select count(m) from Mailbox m where m.isChecked = :isChecked and m.user = :user")

    /**
     * @param id: adminBoard pk
     * @param boardType: 어드민 게시판을 조회하는 것이므로 항상 BoardType.ADMIN
     * @return: dto
     */
//    @Query(
//            value = "select count(l), c from Like l, Comment c where l.boardId = :boardId and " +
//                    "l.boardType = :boardType and c.boardId = :boardId and c.boardType = :boardType"
//    )
//    DetailAdminBoardDto findAdminDetailBoard(@Param("boardId") Long id, @Param("boardType") BoardType boardType);

}
