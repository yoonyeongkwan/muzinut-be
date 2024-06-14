package nuts.muzinut.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class EmailConfigTest {

    @Value("${spring.mail.username}")
    private String emailId;
    @Value("${spring.mail.password}")
    private String emailPassword;

    @Test
    void testValue() {

        log.info("{}", emailId);
        log.info("{}", emailPassword);
    }
}