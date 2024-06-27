package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.Reply;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.board.CommentRepository;
import nuts.muzinut.repository.board.ReplyRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.dto.MessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 대댓글 작성
    @Transactional
    public ResponseEntity<MessageDto> saveReply(Long commentId, String content) {
        String currentUsername = getCurrentUsername();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundEntityException("사용자를 찾을 수 없습니다"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundEntityException("댓글을 찾을 수 없습니다"));

        Reply reply = new Reply();
        reply.addReply(comment, content, user);
        replyRepository.save(reply);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageDto("대댓글이 작성되었습니다."));
    }

    // 대댓글 수정
    @Transactional
    public ResponseEntity<MessageDto> updateReply(Long replyId, String content) {
        String currentUsername = getCurrentUsername();
        Reply reply = checkEntityExists(replyId);
        checkUserAuthorization(reply, currentUsername);

        reply.modifyContent(content);
        replyRepository.save(reply);

        return ResponseEntity.ok().body(new MessageDto("대댓글이 수정되었습니다."));
    }

    // 대댓글 삭제
    @Transactional
    public ResponseEntity<MessageDto> deleteReply(Long replyId) {
        String currentUsername = getCurrentUsername();
        Reply reply = checkEntityExists(replyId);
        checkUserAuthorization(reply, currentUsername);

        replyRepository.delete(reply);

        return ResponseEntity.ok().body(new MessageDto("대댓글이 삭제되었습니다."));
    }

    // 특정 댓글에 달린 모든 대댓글 조회
    public List<Reply> findByComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundEntityException("댓글을 찾을 수 없습니다"));
        return replyRepository.findByComment(comment);
    }

    // 특정 사용자가 작성한 모든 대댓글 조회
    public List<Reply> findByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundEntityException("사용자를 찾을 수 없습니다"));
        return replyRepository.findByUser(user);
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
    private Reply checkEntityExists(Long id) {
        return replyRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("대댓글을 찾을 수 없습니다"));
    }

    // 공통 예외 처리를 위한 메소드
    private void checkUserAuthorization(Reply reply, String username) {
        if (!reply.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("해당 대댓글에 대한 권한이 없습니다");
        }
    }
}
