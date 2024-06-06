package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired MemberRepository memberRepository;
    @Autowired FollowRepository followRepository;
    @PersistenceContext EntityManager em;

    /**
     * 1명의 회원이 2명의 회원을 팔로우 
     */
    @Test
    void basicAddFollow() {

        //given
        Member m1 = new Member("m1", "1234");
        Member m2 = new Member("m2", "1234");
        Member m3 = new Member("m3", "1234");

        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);

        Follow follow1 = new Follow();
        Follow follow2 = new Follow();

        //when
        follow1.createFollowing(m1, m2); //m1 이 m2를 팔로우 했음
        follow2.createFollowing(m1, m3); //m1 이 m3를 팔로우 했음

        //then
        List<Follow> result = followRepository.findAll();


        assertThat(result.size()).isEqualTo(2); //현재 팔로우에 대한 정보는 총 2개

        //m1 이 m2, m3를 팔로워 함
        assertThat(result)
                .extracting("followingMemberId")
                .contains(m2.getId(), m3.getId());

        //팔로우를 한 사람은 m1밖에 없음
        assertThat(result)
                .extracting("member")
                .containsOnly(m1);
    }

    /**
     * m2 가 탈퇴를 했다면 m1 의 팔로우 정보는 m3 만 존재하는가? (논의 필요)
     */
}