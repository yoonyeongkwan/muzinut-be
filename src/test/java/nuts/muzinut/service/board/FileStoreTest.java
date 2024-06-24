package nuts.muzinut.service.board;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

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
}