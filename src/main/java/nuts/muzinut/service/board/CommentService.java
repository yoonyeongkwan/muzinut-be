package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.board.CommentRepository;
import nuts.muzinut.repository.board.query.BoardQueryRepository;
import nuts.muzinut.repository.member.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import nuts.muzinut.dto.MessageDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final UserRepository userRepository;

    // 댓글 작성
    @Transactional
    public ResponseEntity<MessageDto> saveComment(Long boardId, String content) {
        String currentUsername = getCurrentUsername();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundEntityException("사용자를 찾을 수 없습니다"));

        Board board = boardQueryRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundEntityException("게시판을 찾을 수 없습니다"));

        Comment comment = new Comment();
        comment.addComment(user, board, content);
        commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageDto("댓글이 작성되었습니다."));
    }

    // 댓글 수정
    @Transactional
    public ResponseEntity<MessageDto> updateComment(Long commentId, String content) {
        String currentUsername = getCurrentUsername();
        Comment comment = checkEntityExists(commentId);
        checkUserAuthorization(comment, currentUsername);

        comment.modifyContent(content);
        commentRepository.save(comment);

        return ResponseEntity.ok().body(new MessageDto("댓글이 수정되었습니다."));
    }

    // 댓글 삭제
    @Transactional
    public ResponseEntity<MessageDto> deleteComment(Long commentId) {
        String currentUsername = getCurrentUsername();
        Comment comment = checkEntityExists(commentId);
        checkUserAuthorization(comment, currentUsername);

        commentRepository.delete(comment);

        return ResponseEntity.ok().body(new MessageDto("댓글이 삭제되었습니다."));
    }

    // 현재 인증된 사용자의 이름을 반환하는 메소드
    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    // 공통 예외 처리를 위한 메소드
    private Comment checkEntityExists(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("댓글을 찾을 수 없습니다"));
    }

    // 공통 예외 처리를 위한 메소드
    private void checkUserAuthorization(Comment comment, String username) {
        if (!comment.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("해당 댓글에 대한 권한이 없습니다");
        }
    }

}
