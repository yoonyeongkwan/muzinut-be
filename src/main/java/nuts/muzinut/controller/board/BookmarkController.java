package nuts.muzinut.controller.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.service.board.BookmarkService;
import nuts.muzinut.service.member.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<MessageDto> addBookmark(@PathVariable Long id) {

        User user = userService.getUserWithUsername().orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다."));
        return bookmarkService.addBookmark(user, id);
    }
}
