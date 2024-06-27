package nuts.muzinut.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.dto.board.comment.CommentRequestDto;
import nuts.muzinut.service.board.CommentService;
import nuts.muzinut.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/comments/{boardId}")
    public ResponseEntity<MessageDto> addComment(
            @PathVariable("boardId") Long boardId,
            @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.saveComment(boardId, commentRequestDto.getContent());
    }

    // 댓글 수정
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<MessageDto> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.updateComment(commentId, commentRequestDto.getContent());
    }

    // 댓글 삭제
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<MessageDto> deleteComment(@PathVariable("commentId") Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
