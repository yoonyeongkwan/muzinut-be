package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.Chat;
import nuts.muzinut.domain.chat.ChatMember;
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
class ChatMemberRepositoryTest {

    @Autowired ChatMemberRepository chatMemberRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ChatRepository chatRepository;

    @Test
    void participate() {

        //given
        Member m1 = new Member();
        Member m2 = new Member();

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
        Member member = new Member();
        memberRepository.save(member);

        Chat chat = new Chat();
        chatRepository.save(chat);

        //회원이 생성된 채팅방에 들어감
        ChatMember chatMember = new ChatMember();
        chatMember.createChat(chat);
        chatMember.joinChatRoom(member, chat);

        //when
        chatMemberRepository.save(chatMember);

        //then
        Optional<ChatMember> findChatMember = chatMemberRepository.findById(chatMember.getId());
        assertThat(findChatMember.get().getChat()).isEqualTo(chat);
        assertThat(findChatMember.get().getMember()).isEqualTo(member);

        //채팅 연관관계 테스트
        Optional<Chat> findChat = chatRepository.findById(chat.getId());
        assertThat(findChat.get()).isEqualTo(chat);

        //회원 연관관계 테스트
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember.get().getChatMembers().getFirst()).isEqualTo(chatMember);
    }

    //한명의 회원이 여러개의 채팅방에 들어가 있는 상황
    @Test
    void joinSeveralChatRoom() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Chat chat1 = new Chat();
        Chat chat2 = new Chat();
        chatRepository.save(chat1);
        chatRepository.save(chat2);

        //회원이 첫번째 채팅방에 들어감
        ChatMember chatMember1 = new ChatMember();
        chatMember1.createChat(chat1);
        chatMember1.joinChatRoom(member, chat1);

        //회원이 두번째 채팅방에 들어감
        ChatMember chatMember2 = new ChatMember();
        chatMember2.createChat(chat2);
        chatMember2.joinChatRoom(member, chat2);

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
                .extracting("member")
                .containsOnly(member);
    }

    @Test
    void delete() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Chat chat1 = new Chat();
        Chat chat2 = new Chat();
        chatRepository.save(chat1);
        chatRepository.save(chat2);

        //회원이 첫번째 채팅방에 들어감
        ChatMember chatMember = new ChatMember();
        chatMember.createChat(chat1);
        chatMember.joinChatRoom(member, chat1);
        chatMemberRepository.save(chatMember);

        //when
        chatMemberRepository.delete(chatMember);

        //then
        Optional<ChatMember> findChatMember = chatMemberRepository.findById(chatMember.getId());
        assertThat(findChatMember.isEmpty()).isTrue();
    }
}