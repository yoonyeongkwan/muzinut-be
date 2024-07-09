package nuts.muzinut.service.member;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.Block;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.block.MyBlockUsersInfo;
import nuts.muzinut.dto.member.block.MyBlocksDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.repository.member.BlockRepository;
import nuts.muzinut.repository.member.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BlockService {

    private final UserRepository userRepository;
    private final BlockRepository blockRepository;

    public Block addBlockUser(User user, String blockUserNickname) {
        if (user.getNickname().equals(blockUserNickname)) {
            throw new IllegalArgumentException("자기 자신을 차단할 수 없습니다");
        }
        Block block = new Block();
        User blockUser = userRepository.findByNickname(blockUserNickname).orElseThrow(() ->
                new NotFoundMemberException("차단할 회원이 존재하지 않습니다."));
        block.createBlock(user, blockUser);
        return blockRepository.save(block);
    }

    public void cancelBlock(User user, String blockUserNickname) {
        User blockUser = userRepository.findByNickname(blockUserNickname).orElseThrow(
                () -> new NotFoundMemberException("차단할 회원이 존재하지 않습니다."));
        Block block = blockRepository.findByBlockUser(blockUser).orElseThrow(
                () -> new NotFoundEntityException("차단 목록에 없는 사람을 차단 해제 할 수 없습니다"));
        blockRepository.delete(block);
    }

    public MyBlocksDto findBlockUsers(User user) {
        MyBlocksDto myBlocksDto = new MyBlocksDto();
        List<Block> blocks = blockRepository.findBlocksByUser(user);
        blocks.forEach(b -> myBlocksDto.getBlockUsersInfo().add(
                new MyBlockUsersInfo(b.getBlockUser().getId(), b.getBlockUser().getNickname(),
                        b.getBlockTime(), b.getBlockUser().getProfileImgFilename())));
        return myBlocksDto;
    }
}
