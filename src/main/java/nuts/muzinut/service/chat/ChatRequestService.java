package nuts.muzinut.service.chat;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.chat.ChatRequest;
import nuts.muzinut.domain.member.Block;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.chat.ChatRequestList;
import nuts.muzinut.dto.chat.ChatRequestListDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.chat.AlreadyExistRequestException;
import nuts.muzinut.exception.chat.BlockUserException;
import nuts.muzinut.repository.chat.ChatRepository;
import nuts.muzinut.repository.chat.ChatRequestRepository;
import nuts.muzinut.repository.member.BlockRepository;
import nuts.muzinut.service.board.DetailCommon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRequestService extends DetailCommon {

    private final ChatRequestRepository chatRequestRepository;
    private final ChatRepository chatRepository;
    private final BlockRepository blockRepository;

    //메시지 요청을 보내는 메서드
    public void createRequest(User requestUser, User receiveUser, String message) {
        boolean existRequest = chatRequestRepository.existsByRequestUserAndReceiveUser(requestUser, receiveUser);
        if (existRequest) {
            throw new AlreadyExistRequestException("이미 채팅방 요청을 보냈습니다");
        }

        ChatRequest chatRequest = new ChatRequest();
        chatRequest.addChatRequest(requestUser, receiveUser, message);
        chatRequestRepository.save(chatRequest);
    }

    //메시지 요청을 받는 메서드
    public void acceptRequest(User requestUser, User receiveUser) {
        Optional<Block> isBlockUser = blockRepository.findByBlockUser(requestUser);
        if (isBlockUser.isPresent()) {
            throw new BlockUserException("차단 당한 사용자의 채팅방 개설 요청을 받아들일 수 없습니다");
        }

        ChatRequest findRequest = chatRequestRepository.findByRequestUserAndReceiveUser(requestUser, receiveUser)
                .orElseThrow(() -> new NotFoundEntityException("채팅방 요청을 찾을 수 없습니다"));

        findRequest.acceptRequest();
    }

    //모든 요청 메시지들을 반환하는 메서드
    public ChatRequestListDto requestList(User user) {
        List<ChatRequestList> requestList = new ArrayList<>();
        ChatRequestListDto chatRequestListDto = new ChatRequestListDto(requestList);
        List<ChatRequest> requests = chatRequestRepository.findByReceiveUser(user);

        requests.forEach(r -> requestList.add(
                        ChatRequestList.builder()
                                .id(r.getId())
                                .nickname(r.getRequestUser().getNickname())
                                .message(r.getMessage())
                                .profileImg(encodeFileToBase64(r.getRequestUser().getProfileImgFilename()))
                                .build()));

        return chatRequestListDto;
    }
}
