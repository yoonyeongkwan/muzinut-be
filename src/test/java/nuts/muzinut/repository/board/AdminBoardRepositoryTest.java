package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.AdminBoard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Test
    void findAllWithPaging() {

        //given
        createAdminBoards();

        //when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<AdminBoard> page = adminBoardRepository.findAll(pageRequest);

        //then
        List<AdminBoard> content = page.getContent();
        assertThat(content.size()).isEqualTo(10);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getNumber()).isEqualTo(0);
    }

    private void createAdminBoards() {
        for (int i = 0; i < 30; i++) {
            AdminBoard adminBoard = new AdminBoard();
            adminBoardRepository.save(adminBoard);
        }
    }
}