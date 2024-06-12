package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.AdminBoard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminBoardRepositoryTest {

    @Autowired AdminBoardRepository adminBoardRepository;

    @Test
    void save() {

        //given
        AdminBoard adminBoard = new AdminBoard();

        //when
        adminBoardRepository.save(adminBoard);

        //then
        Optional<AdminBoard> findAdminBoard = adminBoardRepository.findById(adminBoard.getId());
        assertThat(findAdminBoard.get()).isEqualTo(adminBoard);
    }

    @Test
    void delete() {

        //given
        AdminBoard adminBoard = new AdminBoard();
        adminBoardRepository.save(adminBoard);

        //when
        adminBoardRepository.delete(adminBoard);

        //then
        Optional<AdminBoard> findAdminBoard = adminBoardRepository.findById(adminBoard.getId());
        assertThat(findAdminBoard.isEmpty()).isTrue();
    }
}