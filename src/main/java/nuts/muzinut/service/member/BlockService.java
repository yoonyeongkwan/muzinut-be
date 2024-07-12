package nuts.muzinut.service.member;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.chat.ChatMember;
import nuts.muzinut.domain.member.Block;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.block.MyBlockUsersInfo;
import nuts.muzinut.dto.member.block.MyBlocksDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.repository.chat.ChatMemberRepository;
import nuts.muzinut.repository.chat.ChatRepository;
import nuts.muzinut.repository.member.BlockRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.board.DetailCommon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BlockService extends DetailCommon {

    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final ChatMemberRepository chatMemberRepository;

    //특정 유저를 차단 -> 채팅방 얼리기
    public Block addBlockUser(User user, String blockUserNickname) {
        if (user.getNickname().equals(blockUserNickname)) {
            throw new IllegalArgumentException("자기 자신을 차단할 수 없습니다");
        }
        Block block = new Block();
        User blockUser = userRepository.findByNickname(blockUserNickname).orElseThrow(() ->
                new NotFoundMemberException("차단할 회원이 존재하지 않습니다."));
        block.createBlock(user, blockUser); //차단하기

        List<ChatMember> chatMembers = chatMemberRepository.getChatMembersByUsers(user, blockUser);
        if (chatMembers.size() == 2) { //채팅방에 두명의 회원이 있다면
            chatMembers.getFirst().getChat().freezeChatRoom(); //채팅방 얼리기
        }

        return blockRepository.save(block);
    }

    public void cancelBlock(User user, String blockUserNickname) {
        User blockUser = userRepository.findByNickname(blockUserNickname).orElseThrow(
                () -> new NotFoundMemberException("차단 해제할 회원이 존재하지 않습니다."));
        Block block = blockRepository.findByBlockUser(blockUser).orElseThrow(
                () -> new NotFoundEntityException("차단 목록에 없는 사람을 차단 해제 할 수 없습니다"));
        blockRepository.delete(block); //차단 취소

        List<ChatMember> chatMembers = chatMemberRepository.getChatMembersByUsers(user, blockUser);
        if (chatMembers.size() == 2) { //채팅방에 두명의 회원이 있다면
            chatMembers.getFirst().getChat().meltChatRoom(); //얼린 채팅방 녹이기 (채팅방 상태 정상으로 변환)
        }
    }

    public MyBlocksDto findBlockUsers(User user) {
        MyBlocksDto myBlocksDto = new MyBlocksDto();
        List<Block> blocks = blockRepository.findBlocksByUser(user);
        blocks.forEach(b -> myBlocksDto.getBlockUsersInfo().add(
                new MyBlockUsersInfo(b.getBlockUser().getId(), b.getBlockUser().getNickname(),
                        b.getBlockTime(), encodeFileToBase64(b.getBlockUser().getProfileImgFilename()))));
        return myBlocksDto;
    }
}
