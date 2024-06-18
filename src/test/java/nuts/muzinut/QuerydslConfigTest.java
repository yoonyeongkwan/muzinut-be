package nuts.muzinut;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import nuts.muzinut.domain.member.QUser;
import nuts.muzinut.domain.member.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.query.DefaultJpaQueryMethodFactory;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuerydslConfigTest {

    @Autowired EntityManager em;

    @Test
    void contextLoads() {

        //given
        User user = new User();
        em.persist(user);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QUser qUser = QUser.user;

        //when
        User result = query
                .selectFrom(qUser)
                .fetchOne();

        //then
        assertThat(result).isEqualTo(user);
    }
}
