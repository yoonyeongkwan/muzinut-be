package nuts.muzinut.service.board;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

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
}