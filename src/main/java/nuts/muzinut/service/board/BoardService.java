package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.BoardType;
import nuts.muzinut.dto.board.board.BoardsDto;
import nuts.muzinut.dto.board.board.BoardsForm;
import nuts.muzinut.exception.NotFoundFileException;
import nuts.muzinut.exception.board.BoardSearchTypeNotExistException;
import nuts.muzinut.repository.board.BoardRepository;
import nuts.muzinut.repository.board.query.BoardQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static nuts.muzinut.domain.board.BoardType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardQueryRepository queryRepository;
    private final BoardRepository boardRepository;
    private BoardsDto boardsDto = new BoardsDto();
    private BoardType boardType;

    //검색 기능
    public BoardsDto searchResult(String title, String searchCond, int startPage) {
        Pageable pageable = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt"));
        if (searchCond.equals("EVENT")) {
            boardType = EVENT;
        } else if (searchCond.equals("FREE")) {
            boardType = FREE;
        } else if (searchCond.equals("ADMIN")) {
            boardType = ADMIN;
        } else if (searchCond.equals("RECRUIT")) {
            boardType = RECRUIT;
        }

        log.info("searchType {}", boardType);

        Page<BoardsForm> page = queryRepository.search(boardType, title, pageable);
        boardsDto.setBoardsForms(page.getContent());
        boardsDto.setPaging(page.getNumber(), page.getTotalPages());
        return boardsDto;
    }

    public String getQuillFilename(String filename) {
        Board findBoard = boardRepository.findByFilename(filename).orElseThrow(() -> new NotFoundFileException("퀼파일이 없습니다"));
        return findBoard.getFilename();
    }
}
