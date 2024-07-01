package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.EventBoard;
import nuts.muzinut.dto.board.event.DetailEventBoardDto;
import nuts.muzinut.repository.board.EventBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventBoardService {

    private final EventBoardRepository eventBoardRepository;

    public EventBoard save(EventBoard eventBoard) {
        return eventBoardRepository.save(eventBoard);
    }

    public DetailEventBoardDto getDetailEventBoard(Long boardId) {
        return null;
    }
}
