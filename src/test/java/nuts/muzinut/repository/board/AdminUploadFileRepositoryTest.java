package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.AdminUploadFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminUploadFileRepositoryTest {

    @Autowired
    AdminBoardRepository adminBoardRepository;
    @Autowired
    AdminUploadFileRepository uploadFileRepository;

    @Test
    void save() {

        //given
        AdminBoard adminBoard = new AdminBoard();
        adminBoardRepository.save(adminBoard);

        AdminUploadFile adminUploadFile = new AdminUploadFile();
        adminUploadFile.addFiles(adminBoard);

        //when
        uploadFileRepository.save(adminUploadFile);

        //then
        Optional<AdminUploadFile> result = uploadFileRepository.findById(adminUploadFile.getId());
        assertThat(result.get()).isEqualTo(adminUploadFile);
        assertThat(result.get().getAdminBoard()).isEqualTo(adminBoard);
    }

    @Test
    void delete() {

        //given
        AdminUploadFile adminUploadFile = new AdminUploadFile();
        uploadFileRepository.save(adminUploadFile);

        //when
        uploadFileRepository.delete(adminUploadFile);

        //then
        Optional<AdminUploadFile> result = uploadFileRepository.findById(adminUploadFile.getId());
        assertThat(result.isEmpty()).isTrue();
    }

    //adminBoard 가 삭제되면 게시판에 해당하는 첨부파일 모두 삭제
    @Test
    void deleteAdminBoard() {

        //given
        AdminBoard adminBoard = new AdminBoard();
        adminBoardRepository.save(adminBoard);

        AdminUploadFile adminUploadFile = new AdminUploadFile();
        adminUploadFile.addFiles(adminBoard);
        uploadFileRepository.save(adminUploadFile);

        //when
        adminBoardRepository.delete(adminBoard);

        //then
        Optional<AdminUploadFile> result = uploadFileRepository.findById(adminUploadFile.getId());
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void getAdminFiles() {

        //given
        AdminBoard adminBoard = new AdminBoard();
        AdminUploadFile f1 = new AdminUploadFile("store1", "origin1");
        f1.addFiles(adminBoard);
        AdminUploadFile f2 = new AdminUploadFile("store2", "origin2");
        f2.addFiles(adminBoard);
        uploadFileRepository.save(f1);
        uploadFileRepository.save(f2);

        //when
        List<AdminUploadFile> files = uploadFileRepository.getAdminUploadFile(adminBoard.getId());

        //then
        assertThat(files.size()).isEqualTo(2);

        assertThat(files)
                .extracting("storeFilename")
                .contains("store1", "store2");

        assertThat(files)
                .extracting("originFilename")
                .contains("origin1", "origin2");
    }
}