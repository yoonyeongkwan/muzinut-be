package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class FollowRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired FollowRepository followRepository;
    @PersistenceContext EntityManager em;

    /**
     * 1명의 회원이 2명의 회원을 팔로우 
     */
    @Test
    void basicAddFollow() {

        //given
        List<User> users = createFollows();

        //when
        List<Follow> result = followRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2); //현재 팔로우에 대한 정보는 총 2개

        //m1 이 m2, m3를 팔로워 함
        assertThat(result)
                .extracting("followingMemberId")
                .contains(users.get(1).getId(), users.get(2).getId());

        //팔로우를 한 사람은 m1밖에 없음
        assertThat(result)
                .extracting("user")
                .containsOnly(users.get(0));
    }

    //팔로잉 카운트 (내가 팔로우 한 사람들)
    @Test
    void totalFollowingCount() {

        //given
        List<User> users = createFollows();

        //when
        Long count = followRepository.countFollowingByUser(users.getFirst());

        //then
        assertThat(count).isEqualTo(2); //u1 이 팔로우 한 사람은 2명
    }

    //팔로워 카운트 (나를 팔로우 한 사람들)
    @Test
    void totalFollowedCount() {

        //given
        List<User> users = createFollows();

        //when
        Long u2Follower = followRepository.countFollowerByFollowingMemberId(users.get(1).getId());
        Long u3Follower = followRepository.countFollowerByFollowingMemberId(users.get(2).getId());

        //then
        assertThat(u2Follower).isEqualTo(1); //u2 의 팔로워는 u1
        assertThat(u3Follower).isEqualTo(1); //u3 의 팔로워는 u1
    }

    //특정 아티스트(회원)의 알림을 끄기
    @Test
    void turnOffNotice() {

        //given
        List<User> users = createFollow(); //users[0] 가 users[1] 을 팔로우
        User u1 = users.getFirst();
        User artist = users.get(1);

        //when
        followRepository.turnOffNotification(false, u1, artist.getId()); //알람 끄기
        clearContext();

        //then
        List<Follow> result = followRepository.findByUser(u1);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst().getNotification()).isFalse(); //알람 여부가 false (즉 알람 안 받음)
    }

    //특정 아티스트(회원)의 알림을 켜기
    @Test
    void turnOnNotice() {

        //given
        List<User> users = createFollow(); //users[0] 가 users[1] 을 팔로우
        User u1 = users.getFirst();
        User artist = users.get(1);
        followRepository.turnOffNotification(false, u1, artist.getId()); //알람 끄기
        clearContext();

        //when
        followRepository.turnOnNotification(true, u1, artist.getId());
        clearContext();

        //then
        List<Follow> result = followRepository.findByUser(u1);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst().getNotification()).isTrue(); //알람 여부가 false (즉 알람 안 받음)
    }


    private List<User> createFollows() {
        User u1 = new User("m1@naver.com", "1234");
        User u2 = new User("m2@naver.com", "1234");
        User u3 = new User("m3@naver.com", "1234");

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);

        Follow follow1 = new Follow();
        Follow follow2 = new Follow();

        //when
        follow1.createFollowing(u1, u2); //m1 이 m2를 팔로우 했음
        follow2.createFollowing(u1, u3); //m1 이 m3를 팔로우 했음
        followRepository.save(follow1);
        followRepository.save(follow2);

        return List.of(u1, u2, u3);
    }

    private List<User> createFollow() {
        User u1 = new User("m1@naver.com", "1234");
        User u2 = new User("m2@naver.com", "1234");

        userRepository.save(u1);
        userRepository.save(u2);

        Follow follow1 = new Follow();

        //when
        follow1.createFollowing(u1, u2); //m1 이 m2를 팔로우 했음
        followRepository.save(follow1);

        return List.of(u1, u2);
    }

    private void clearContext() {
        em.flush();
        em.clear();
    }
}