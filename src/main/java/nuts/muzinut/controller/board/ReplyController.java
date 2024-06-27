package nuts.muzinut.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.dto.board.comment.ReplyRequestDto;
import nuts.muzinut.service.board.ReplyService;
import nuts.muzinut.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    // 대댓글 작성
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/replies/{commentId}")
    public ResponseEntity<MessageDto> addReply(
            @PathVariable("commentId") Long commentId,
            @RequestBody ReplyRequestDto replyRequestDto) {
        return replyService.saveReply(commentId, replyRequestDto.getContent());
    }

    // 대댓글 수정
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/replies/{replyId}")
    public ResponseEntity<MessageDto> updateReply(
            @PathVariable("replyId") Long replyId,
            @RequestBody ReplyRequestDto replyRequestDto) {
        return replyService.updateReply(replyId, replyRequestDto.getContent());
    }

    // 대댓글 삭제
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<MessageDto> deleteReply(@PathVariable("replyId") Long replyId) {
        return replyService.deleteReply(replyId);
    }
}
