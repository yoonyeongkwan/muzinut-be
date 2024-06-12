package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.Chat;
import nuts.muzinut.domain.chat.Message;
import nuts.muzinut.domain.member.Member;
import nuts.muzinut.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    MessageRepository messageRepository;

    @Test
    void save() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Chat chat = new Chat();
        chatRepository.save(chat);

        Message msg1 = new Message();
        Message msg2 = new Message();

        //when
        msg1.createMessage(member, chat, "msg1");
        msg2.createMessage(member, chat, "msg2");
        messageRepository.save(msg1);
        messageRepository.save(msg2);

        //then
        List<Message> result = messageRepository.findAll();

        //채팅방에 올라온 메시지는 2개
        assertThat(result)
                .extracting("message")
                .contains("msg1", "msg2");

        //채팅방은 1개
        assertThat(result)
                .extracting("chat")
                .containsOnly(chat);

        //채팅을 작성한 인원은 1명
        assertThat(result)
                .extracting("member")
                .containsOnly(member);
    }

    @Test
    void delete() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Chat chat = new Chat();
        chatRepository.save(chat);

        Message msg1 = new Message();
        msg1.createMessage(member, chat, "msg1");
        messageRepository.save(msg1);

        //when
        messageRepository.delete(msg1);

        //then
        Optional<Message> findMessage = messageRepository.findById(msg1.getId());
        assertThat(findMessage.isEmpty()).isTrue();
    }
}