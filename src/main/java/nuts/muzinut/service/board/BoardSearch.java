package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.BoardType;
import nuts.muzinut.dto.board.board.BoardsDto;
import nuts.muzinut.dto.board.board.BoardsForm;
import nuts.muzinut.repository.board.query.BoardQueryRepository;
import org.springframework.data.domain.*;

import java.util.List;

@RequiredArgsConstructor
public abstract class BoardSearch {

    private final BoardQueryRepository queryRepository;
    private BoardsDto boardsDto = new BoardsDto();

    public BoardsDto searchResult(String searchCond, BoardType boardType, int startPage) {
        Pageable pageable = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt"));
        Page<BoardsForm> page = queryRepository.search(boardType, searchCond, pageable);
        boardsDto.setBoardsForms(page.getContent());
        boardsDto.setPaging(page.getNumber(), page.getTotalPages());
        return boardsDto;
    }
}
