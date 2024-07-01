package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.CommentLike;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.board.CommentLikeRepository;
import nuts.muzinut.repository.board.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository likeRepository;
    private final CommentRepository commentRepository;

    //댓글에 대해 좋아요 & 좋아요 취소를 수행하는 메서드
    public ResponseEntity<MessageDto> commentLike(User user, Long commentId) {

        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundEntityException("없는 댓글 입니다."));

        if (!(likeRepository.existsByUserAndComment(user, findComment))) {
            // 좋아요가 눌려 있지 않으면(기본) 좋아요 추가
            CommentLike commentLike = new CommentLike();
            commentLike.addLike(user, findComment);
            likeRepository.save(commentLike);
            return ResponseEntity.ok().body(new MessageDto("댓글 좋아요가 완료되었습니다."));
        } else {
            // 좋아요가 이미 눌려 있으면 좋아요 삭제
            likeRepository.deleteByUserAndBoard(user, commentId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageDto("댓글 좋아요가 취소되었습니다."));
        }
    }
}
