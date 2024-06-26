package nuts.muzinut.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.service.board.LikeService;
import nuts.muzinut.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 좋아요 추가
    @PostMapping("/likes/{boardId}")
    public ResponseEntity<MessageDto> saveLike(@PathVariable(value = "boardId") Long boardId) {
        log.info("Adding like to board with ID: {}", boardId);
        return likeService.saveLike(boardId);
    }

    // 좋아요 삭제
    @DeleteMapping("/likes/{boardId}")
    public ResponseEntity<MessageDto> deleteLike(@PathVariable("boardId") Long boardId) {
        log.info("Deleting like from board with ID: {}", boardId);
        return likeService.deleteLike(boardId);
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
