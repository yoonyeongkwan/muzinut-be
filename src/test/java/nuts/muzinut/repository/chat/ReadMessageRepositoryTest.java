package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.Message;
import nuts.muzinut.domain.chat.ReadMessage;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.member.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ReadMessageRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired MessageRepository messageRepository;
    @Autowired ReadMessageRepository readMessageRepository;

    @Test
    void save() {

        //given
        User user = new User();
        userRepository.save(user);

        Message message = new Message();
        messageRepository.save(message);

        ReadMessage readMessage = new ReadMessage();
        readMessage.read(user, message);

        //when
        readMessageRepository.save(readMessage);

        //then
        Optional<ReadMessage> result = readMessageRepository.findById(readMessage.getId());
        assertThat(result.get().getMessage()).isEqualTo(message);
    }

     @Test
     void delete() {

         //given
         User user = new User();
         userRepository.save(user);

         Message message = new Message();
         messageRepository.save(message);

         ReadMessage readMessage = new ReadMessage();
         readMessage.read(user, message);

         readMessageRepository.save(readMessage);

         //when
         readMessageRepository.delete(readMessage);

         //then
         Optional<ReadMessage> result = readMessageRepository.findById(readMessage.getId());
         assertThat(result.isEmpty()).isTrue();
     }
}