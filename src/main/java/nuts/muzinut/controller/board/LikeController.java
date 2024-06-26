package nuts.muzinut.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.service.board.LikeService;
import nuts.muzinut.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 좋아요 토글
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/likes/{boardId}")
    public ResponseEntity<MessageDto> toggleLike(@PathVariable(value = "boardId") Long boardId) {
        log.info("Toggling like for board with ID: {}", boardId);
        return likeService.toggleLike(boardId);
    }


    // 특정 게시글의 좋아요 수 반환(확인용)
    @GetMapping("/likes/{boardId}/count")
    public ResponseEntity<Map<String, Long>> countLikes(@PathVariable("boardId") Long boardId) {
        log.info("Counting likes for board with ID: {}", boardId);
        Long likeCount = likeService.countLikes(boardId);
        Map<String, Long> response = new HashMap<>();
        response.put("likeCount", likeCount);
        return ResponseEntity.ok(response);
    }
}
