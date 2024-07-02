package nuts.muzinut.controller.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.service.board.CommentLikeService;
import nuts.muzinut.service.member.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/comment-like")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService likeService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<MessageDto> commentLike(@PathVariable Long id) {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("없는 회원입니다."));

        return likeService.commentLike(user, id);
    }
}
