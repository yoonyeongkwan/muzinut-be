package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.board.CommentRepository;
import nuts.muzinut.repository.board.query.BoardQueryRepository;
import nuts.muzinut.repository.member.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import nuts.muzinut.dto.MessageDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final UserRepository userRepository;

    // 댓글 작성
    @Transactional
    public ResponseEntity<MessageDto> addComment(Long boardId, Long userId, String content) {
        Optional<Board> optionalBoard = boardQueryRepository.findById(boardId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalBoard.isPresent() && optionalUser.isPresent()) {
            Board board = optionalBoard.get();
            User user = optionalUser.get();
            Comment comment = new Comment();
            comment.addComment(user, board, content);
            commentRepository.save(comment);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageDto("댓글이 작성되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new MessageDto("댓글 작성 권한이 없습니다."));
        }
    }

    // 댓글 수정
    @Transactional
    public ResponseEntity<MessageDto> updateComment(Long commentId, String content) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.modifyContent(content);
            commentRepository.save(comment);
            return ResponseEntity.ok()
                    .body(new MessageDto("댓글이 수정되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageDto("댓글을 찾을 수 없습니다."));
        }
    }

    // 댓글 삭제
    @Transactional
    public ResponseEntity<MessageDto> deleteComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isPresent()) {
            commentRepository.deleteById(commentId);
            return ResponseEntity.ok()
                    .body(new MessageDto("댓글이 삭제되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageDto("댓글을 찾을 수 없습니다."));
        }
    }
}
