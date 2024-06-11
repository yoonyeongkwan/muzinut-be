package nuts.muzinut.service.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RedisUtilTest {

    @Autowired private RedisUtil redisUtil;

    @Test
    void redisTest() {

        //given
        String email = "test@test.com";
        String code = "aaa111";

        //when
        redisUtil.setDataExpire(email, code, 60 * 60L);

        //then
        assertThat(redisUtil.getData(email)).isEqualTo("aaa111");
        assertThat(redisUtil.existData(email)).isTrue();
    }

    @Test
    void mailTest() {

        //given

        //when

        //then

    }
}