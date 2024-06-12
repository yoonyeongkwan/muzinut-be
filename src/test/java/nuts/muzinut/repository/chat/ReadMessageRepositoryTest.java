package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.Message;
import nuts.muzinut.domain.chat.ReadMessage;
import nuts.muzinut.domain.member.Member;
import nuts.muzinut.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReadMessageRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired MessageRepository messageRepository;
    @Autowired ReadMessageRepository readMessageRepository;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Message message = new Message();
        messageRepository.save(message);

        ReadMessage readMessage = new ReadMessage();
        readMessage.read(member, message);

        //when
        readMessageRepository.save(readMessage);

        //then
        Optional<ReadMessage> result = readMessageRepository.findById(readMessage.getId());
        assertThat(result.get().getMessage()).isEqualTo(message);
    }

     @Test
     void delete() {

         //given
         Member member = new Member();
         memberRepository.save(member);

         Message message = new Message();
         messageRepository.save(message);

         ReadMessage readMessage = new ReadMessage();
         readMessage.read(member, message);

         readMessageRepository.save(readMessage);

         //when
         readMessageRepository.delete(readMessage);

         //then
         Optional<ReadMessage> result = readMessageRepository.findById(readMessage.getId());
         assertThat(result.isEmpty()).isTrue();
     }
}