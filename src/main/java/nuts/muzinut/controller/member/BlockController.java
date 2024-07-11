package nuts.muzinut.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.Block;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.member.block.BlockForm;
import nuts.muzinut.dto.member.block.MyBlockUsersInfo;
import nuts.muzinut.dto.member.block.MyBlocksDto;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.service.board.FileStore;
import nuts.muzinut.service.member.BlockService;
import nuts.muzinut.service.member.UserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlockController {

    private final BlockService blockService;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final FileStore fileStore;

    //차단하기
    @ResponseBody
    @PostMapping("/block")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public MessageDto addBlock(@RequestBody @Validated BlockForm form) {
        User user = userService.getUserWithUsername().orElseThrow(() -> new NotFoundMemberException("누군가를 차단할 수 없습니다"));
        Block block = blockService.addBlockUser(user, form.getBlockUserNickname());
        return new MessageDto(block.getBlockUser().getNickname() + "님을 차단하였습니다");
    }

    //차단 해제
    @ResponseBody
    @DeleteMapping("/block")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public MessageDto cancelBlock(@RequestBody @Validated BlockForm form) {
        User user = userService.getUserWithUsername().orElseThrow(() -> new NotFoundMemberException("누군가를 차단할 수 없습니다"));
        blockService.cancelBlock(user, form.getBlockUserNickname());
        return new MessageDto(form.getBlockUserNickname() + "님을 차단 해제 하였습니다.");
    }

    //나의 차단한 사람들 목록 확인
    @ResponseBody
    @GetMapping(value = "/my-blocks", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public MyBlocksDto getBlockList() throws JsonProcessingException {
        User user = userService.getUserWithUsername().orElseThrow(() -> new NotFoundMemberException("차단 목록을 조회할 수 없습니다"));
        return blockService.findBlockUsers(user);
    }
}
