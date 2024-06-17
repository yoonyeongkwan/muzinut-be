package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.Chat;
import nuts.muzinut.domain.chat.Message;
import nuts.muzinut.domain.chat.RoomType;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    MessageRepository messageRepository;

    @Test
    void save() {

        //given
        Chat chat = new Chat(RoomType.DM);

        //when
        chatRepository.save(chat);

        //then
        Optional<Chat> findChat = chatRepository.findById(chat.getId());
        assertThat(findChat.get()).isEqualTo(chat);
        assertThat(findChat.get().getRoomType()).isEqualTo(RoomType.DM);
    }

    @Test
    void delete() {

        //given
        Chat chat = new Chat(RoomType.DM);
        chatRepository.save(chat);

        //when
        chatRepository.delete(chat);

        //then
        Optional<Chat> findChat = chatRepository.findById(chat.getId());
        assertThat(findChat.isEmpty()).isTrue();

    }

    //채팅방이 삭제되면 그 채팅방에 해당하는 메시지 모두 삭제
    @Test
    void deleteWithMessage() {

        //given
        User user1 = new User();
        userRepository.save(user1);

        User user2 = new User();
        userRepository.save(user2);

        Chat chat = new Chat();
        chatRepository.save(chat);

        Message msg1 = new Message();
        Message msg2 = new Message();

        //하나의 채팅방에 2개의 메시지가 있는 상황
        msg1.createMessage(user1, chat, "msg1");
        msg2.createMessage(user2, chat, "msg2");
        messageRepository.save(msg1);
        messageRepository.save(msg2);

        //when
        chatRepository.delete(chat);

        //then
        List<Message> messages = messageRepository.findAll();
        assertThat(messages.size()).isEqualTo(0); //채팅방을 삭제 했으므로 그 채팅방의 메시지는 없다.

        Optional<Chat> findChat = chatRepository.findById(chat.getId());
        assertThat(findChat.isEmpty()).isTrue();
    }
}