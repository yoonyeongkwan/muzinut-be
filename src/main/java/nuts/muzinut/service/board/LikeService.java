package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Like;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.board.LikeRepository;
import nuts.muzinut.repository.board.query.BoardQueryRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.dto.MessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final UserRepository userRepository;

    // 좋아요 추가
    @Transactional
    public ResponseEntity<MessageDto> saveLike(Long boardId) {
        String currentUsername = getCurrentUsername();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundEntityException("사용자를 찾을 수 없습니다"));

        Board board = boardQueryRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundEntityException("게시판을 찾을 수 없습니다"));

        if (likeRepository.existsByUserAndBoard(user, board)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageDto("이미 좋아요를 눌렀습니다."));
        }

        Like like = new Like();
        like.addLike(user, board);
        likeRepository.save(like);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageDto("좋아요가 추가되었습니다."));
    }

    // 좋아요 삭제
    @Transactional
    public ResponseEntity<MessageDto> deleteLike(Long boardId) {
        String currentUsername = getCurrentUsername();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundEntityException("사용자를 찾을 수 없습니다"));

        Board board = boardQueryRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundEntityException("게시판을 찾을 수 없습니다"));

        if (!likeRepository.existsByUserAndBoard(user, board)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageDto("좋아요를 누르지 않았습니다."));
        }

        likeRepository.deleteByUserAndBoard(user, board);

        return ResponseEntity.ok().body(new MessageDto("좋아요가 삭제되었습니다."));
    }

    // 특정 게시글의 좋아요 수 반환
    public Long countLikes(Long boardId) {
        Board board = boardQueryRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundEntityException("게시판을 찾을 수 없습니다"));

        return likeRepository.countByBoard(board);
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
}
