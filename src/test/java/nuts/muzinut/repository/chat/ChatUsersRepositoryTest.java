package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.Chat;
import nuts.muzinut.domain.chat.ChatMember;
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
class ChatUsersRepositoryTest {

    @Autowired ChatMemberRepository chatMemberRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired ChatRepository chatRepository;

    @Test
    void participate() {

        //given
        User m1 = new User();
        User m2 = new User();

        Chat chat = new Chat();
        ChatMember chatMember = new ChatMember();

        //when
        chatMember.createChat(chat);
        chatMember.joinChatRoom(m1, chat);
        chatMember.joinChatRoom(m2, chat);

        //then
        assertThat(chat.getParticipateCount()).isEqualTo(2);
    }

    @Test
    void save() {

        //given
        User user = new User();
        userRepository.save(user);

        Chat chat = new Chat();
        chatRepository.save(chat);

        //회원이 생성된 채팅방에 들어감
        ChatMember chatMember = new ChatMember();
        chatMember.createChat(chat);
        chatMember.joinChatRoom(user, chat);

        //when
        chatMemberRepository.save(chatMember);

        //then
        Optional<ChatMember> findChatMember = chatMemberRepository.findById(chatMember.getId());
        assertThat(findChatMember.get().getChat()).isEqualTo(chat);
        assertThat(findChatMember.get().getUser()).isEqualTo(user);

        //채팅 연관관계 테스트
        Optional<Chat> findChat = chatRepository.findById(chat.getId());
        assertThat(findChat.get()).isEqualTo(chat);

        //회원 연관관계 테스트
        Optional<User> findMember = userRepository.findById(user.getId());
        assertThat(findMember.get().getChatMembers().getFirst()).isEqualTo(chatMember);
    }

    //한명의 회원이 여러개의 채팅방에 들어가 있는 상황
    @Test
    void joinSeveralChatRoom() {

        //given
        User user = new User();
        userRepository.save(user);

        Chat chat1 = new Chat();
        Chat chat2 = new Chat();
        chatRepository.save(chat1);
        chatRepository.save(chat2);

        //회원이 첫번째 채팅방에 들어감
        ChatMember chatMember1 = new ChatMember();
        chatMember1.createChat(chat1);
        chatMember1.joinChatRoom(user, chat1);

        //회원이 두번째 채팅방에 들어감
        ChatMember chatMember2 = new ChatMember();
        chatMember2.createChat(chat2);
        chatMember2.joinChatRoom(user, chat2);

        //when
        chatMemberRepository.save(chatMember1);
        chatMemberRepository.save(chatMember2);

        //then
        List<ChatMember> result = chatMemberRepository.findAll();
        List<Chat> findChats = chatRepository.findAll();

        //채팅방은 총 2개가 있다
        assertThat(result)
                .extracting("chat")
                .contains(chat1, chat2);

        assertThat(findChats.size()).isEqualTo(2);

        //회원은 1명
        assertThat(result)
                .extracting("user")
                .containsOnly(user);
    }

    @Test
    void delete() {

        //given
        User user = new User();
        userRepository.save(user);

        Chat chat1 = new Chat();
        Chat chat2 = new Chat();
        chatRepository.save(chat1);
        chatRepository.save(chat2);

        //회원이 첫번째 채팅방에 들어감
        ChatMember chatMember = new ChatMember();
        chatMember.createChat(chat1);
        chatMember.joinChatRoom(user, chat1);
        chatMemberRepository.save(chatMember);

        //when
        chatMemberRepository.delete(chatMember);

        //then
        Optional<ChatMember> findChatMember = chatMemberRepository.findById(chatMember.getId());
        assertThat(findChatMember.isEmpty()).isTrue();
    }
}