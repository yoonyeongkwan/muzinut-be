package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Bookmark;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.exception.board.BoardNotFoundException;
import nuts.muzinut.repository.board.BoardRepository;
import nuts.muzinut.repository.board.BookmarkRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BoardRepository boardRepository;

    public ResponseEntity<MessageDto> addBookmark(User user, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("북마크 한 게시판이 없습니다"));

        if (bookmarkRepository.existsByUserAndBoard(user, board)) {
            bookmarkRepository.deleteByUserAndBoard(user, boardId); //북마크를 이미한 경우는 취소
            return ResponseEntity.ok().body(new MessageDto("북마크가 취소되었습니다."));
        } else {
            Bookmark bookmark = new Bookmark();
            bookmark.addBookmark(user, board);
            bookmarkRepository.save(bookmark); //북마크 저장
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageDto("북마크가 적용되었습니다."));
        }
    }
}
