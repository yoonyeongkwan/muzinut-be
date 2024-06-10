package nuts.muzinut.repository.nuts;

import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.nuts.SupportMsg;
import nuts.muzinut.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SupportMsgRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SupportMsgRepository supportMsgRepository;

    @Test
    void save() {

        //given
        Member artist = new Member();
        memberRepository.save(artist);
        Member sponsor = new Member();
        memberRepository.save(sponsor);

        SupportMsg supportMsg = new SupportMsg();
        supportMsg.addSupportMsg(artist, sponsor, "후원받아라", 100);

        //when
        supportMsgRepository.save(supportMsg);

        //then
        Optional<SupportMsg> findSupportMsg = supportMsgRepository.findById(supportMsg.getId());
        assertThat(findSupportMsg.get()).isEqualTo(supportMsg);
        assertThat(findSupportMsg.get().getMember()).isEqualTo(artist);
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
        Member artist = new Member();
        memberRepository.save(artist);
        Member sponsor = new Member();
        memberRepository.save(sponsor);

        SupportMsg supportMsg = new SupportMsg();
        supportMsg.addSupportMsg(artist, sponsor, "후원받아라", 100);
        supportMsgRepository.save(supportMsg);

        //when
        memberRepository.delete(artist);

        //then
        Optional<SupportMsg> findSupportMsg = supportMsgRepository.findById(supportMsg.getId());
        assertThat(findSupportMsg.isEmpty()).isTrue();
    }
}