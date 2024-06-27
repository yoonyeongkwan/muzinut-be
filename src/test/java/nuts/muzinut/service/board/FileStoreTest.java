package nuts.muzinut.service.board;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class FileStoreTest {

    @Autowired
    FileStore fileStore;

    @Value("${spring.file.dir}")
    private String fileDir;

    @Test
    void fileDir() {

        log.info("filedir: {}", fileDir);
    }

    @Test
    void deleteFile() {

        File file = new File(fileDir + "/8cb0f54a-f530-4d6b-bfce-d425e12b6cbc.txt"); //파일명 적절히 기재
        file.delete();
    }

    @Test
    void streamTest() {

        //given
        List<String> profileImages = List.of("a.png", "b.jpeg", "c.png", "k.gif");

        //when
        List<String> pngFullPath = profileImages.stream()
                .filter(i -> fileStore.extractExt(i).equals("png"))
                .map(fileStore::getFullPath)
                .toList();
        List<String> jpegFullPath = profileImages.stream()
                .filter(i -> fileStore.extractExt(i).equals("jpeg"))
                .map(fileStore::getFullPath)
                .toList();
        List<String> gifFullPath = profileImages.stream()
                .filter(i -> fileStore.extractExt(i).equals("gif"))
                .map(fileStore::getFullPath)
                .toList();
        //then
        assertThat(pngFullPath.size()).isEqualTo(2);
        assertThat(jpegFullPath.size()).isEqualTo(1);
        assertThat(gifFullPath.size()).isEqualTo(1);
    }
}