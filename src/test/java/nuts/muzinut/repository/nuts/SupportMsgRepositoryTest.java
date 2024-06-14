package nuts.muzinut.repository.nuts;

import nuts.muzinut.domain.member.User;
import nuts.muzinut.domain.nuts.SupportMsg;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class SupportMsgRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SupportMsgRepository supportMsgRepository;

    @Test
    void save() {

        //given
        User artist = new User();
        userRepository.save(artist);
        User sponsor = new User();
        userRepository.save(sponsor);

        SupportMsg supportMsg = new SupportMsg();
        supportMsg.addSupportMsg(artist, sponsor, "후원받아라", 100);

        //when
        supportMsgRepository.save(supportMsg);

        //then
        Optional<SupportMsg> findSupportMsg = supportMsgRepository.findById(supportMsg.getId());
        assertThat(findSupportMsg.get()).isEqualTo(supportMsg);
        assertThat(findSupportMsg.get().getUser()).isEqualTo(artist);
        assertThat(findSupportMsg.get().getSponsorId()).isEqualTo(sponsor.getId());
    }

    @Test
    void delete() {

        //given
        SupportMsg supportMsg = new SupportMsg();
        supportMsgRepository.save(supportMsg);

        //when
        supportMsgRepository.delete(supportMsg);

        //then
        Optional<SupportMsg> findSupportMsg = supportMsgRepository.findById(supportMsg.getId());
        assertThat(findSupportMsg.isEmpty()).isTrue();
    }

    //후원 받은 회원 계정이 삭제되면 그 회원의 후원 메시지 내용도 삭제(논의 필요)
    @Test
    void deleteMember() {

        //given
        User artist = new User();
        userRepository.save(artist);
        User sponsor = new User();
        userRepository.save(sponsor);

        SupportMsg supportMsg = new SupportMsg();
        supportMsg.addSupportMsg(artist, sponsor, "후원받아라", 100);
        supportMsgRepository.save(supportMsg);

        //when
        userRepository.delete(artist);

        //then
        Optional<SupportMsg> findSupportMsg = supportMsgRepository.findById(supportMsg.getId());
        assertThat(findSupportMsg.isEmpty()).isTrue();
    }
}