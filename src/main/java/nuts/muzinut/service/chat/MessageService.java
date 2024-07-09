package nuts.muzinut.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.chat.Chat;
import nuts.muzinut.domain.chat.Message;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.repository.chat.ChatMemberRepository;
import nuts.muzinut.repository.chat.ChatRepository;
import nuts.muzinut.repository.chat.MessageRepository;
import nuts.muzinut.repository.chat.ReadMessageRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.member.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final RedisUtil redisUtil;

    /**
     * 한명이 채팅방에 입장했을 때 메시지 읽음 처리
     * @param roomNumber: 채팅방 번호
     * @param username: 채팅방에 입장한 유저
     */
    public void readOneUserMessage(String roomNumber, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundMemberException("없는 회원입니다."));
        Chat chat = chatRepository.findById(Long.parseLong(roomNumber))
                .orElseThrow(() -> new NotFoundEntityException("없는 채팅방입니다."));

        messageRepository.updateOnePersonRead(chat, user);
    }

    /**
     * 메시지 생성
     * @param roomId: 채팅방 pk
     * @param sender: 메시지를 보내는 사람의 nickname
     * @param sendTo: 메시지를 받는 사람의 nickname
     * @param message: 보낼 메시지
     */
    public Message createMessage(Long roomId, String sender, String sendTo, String message) {
        Chat findChatRoom = chatRepository.findById(roomId).orElseThrow(() -> new NotFoundEntityException("없는 채팅방입니다"));
        List<String> chatParticipants = redisUtil.getMultiData(roomId.toString());
        List<User> users = userRepository.findUsersByNickname(sender, sendTo);
        Optional<User> sendUser = users.stream().filter(u -> u.getNickname().equals(sender)).findFirst();

        //채팅방에는 반드시 두명의 회원이 있어야지 메시지를 보낼 수 있음
        if (users.size() == 2 && sendUser.isPresent()) {
            Message msg = new Message();
            if (chatParticipants.size() == 2) { //두명이 채팅방에 참가하고 있으면 모든 메시지는 읽음 처리
                msg.createReadMessage(sendUser.get(), findChatRoom, message);
            } else {
                msg.createNotReadMessage(sendUser.get(), findChatRoom, message); //아닌 경우 메시지 안읽음 처리
            }
            return messageRepository.save(msg);
        }

        throw new NotFoundMemberException("없는 회원과 대화할 수 없습니다");
    }
}
