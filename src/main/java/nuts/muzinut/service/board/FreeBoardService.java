package nuts.muzinut.service.board;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.board.admin.AdminBoardsDto;
import nuts.muzinut.dto.board.admin.AdminBoardsForm;
import nuts.muzinut.dto.board.admin.DetailAdminBoardDto;
import nuts.muzinut.dto.board.comment.CommentDto;
import nuts.muzinut.dto.board.comment.ReplyDto;
import nuts.muzinut.dto.board.free.DetailFreeBoardDto;
import nuts.muzinut.dto.board.free.FreeBoardsDto;
import nuts.muzinut.dto.board.free.FreeBoardsForm;
import nuts.muzinut.exception.BoardNotExistException;
import nuts.muzinut.exception.BoardNotFoundException;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.board.BoardRepository;
import nuts.muzinut.repository.board.FreeBoardRepository;
import nuts.muzinut.repository.board.query.FreeBoardQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QFreeBoard.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;
    private final BoardRepository boardRepository;
    private final FreeBoardQueryRepository queryRepository;

    public FreeBoard save(FreeBoard freeBoard) {
        return freeBoardRepository.save(freeBoard);
    }

    public FreeBoard getFreeBoardForUpdate(Long id) {
        return freeBoardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("찾고자하는 자유게시판이 없습니다"));
    }

    public FreeBoardsDto getFreeBoards(int startPage) throws BoardNotExistException {
        PageRequest pageRequest = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt")); //Todo 한 페이지에 가져올 게시판 수를 정하기
        Page<FreeBoard> page = freeBoardRepository.findAll(pageRequest);
        List<FreeBoard> freeBoards = page.getContent();

        if (freeBoards.isEmpty()) {
            throw new BoardNotExistException("어드민 게시판이 존재하지 않습니다.");
        }

        FreeBoardsDto boardsDto = new FreeBoardsDto();
        boardsDto.setPaging(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        for (FreeBoard f : freeBoards) {
            boardsDto.getFreeBoardsForms().add(new FreeBoardsForm(f.getTitle(), f.getUser().getNickname(),
                    f.getCreatedDt(), f.getLikes().size(), f.getView()));
        }
        return boardsDto;
    }

    /**
     * 특정 자유 게시판 조회
     * tuple (board, freeBoard, like.count)
     */
    public DetailFreeBoardDto detailFreeBoard(Long boardId) {
        List<Tuple> result = queryRepository.getDetailFreeBoard(boardId);

        log.info("tuple: {}", result);
        if (result.isEmpty()) {
            return null;
        }

        Tuple first = result.getFirst();
        Board findBoard = first.get(board);
        FreeBoard findFreeBoard = first.get(freeBoard);
        int view = findFreeBoard.addView();

        if (findBoard == null || findFreeBoard == null) {
            return null;
        }

        DetailFreeBoardDto detailFreeBoardDto =
                new DetailFreeBoardDto(findFreeBoard.getTitle(), findFreeBoard.getUser().getNickname(),
                        view, findFreeBoard.getFilename());
        Long likeCount = first.get(2, Long.class);
        detailFreeBoardDto.setLikeCount(likeCount); //좋아요 수 셋팅

        //댓글 및 대댓글 dto 에 셋팅
        List<CommentDto> comments = new ArrayList<>();
        for (Comment c : findBoard.getComments()) {
            CommentDto commentDto = new CommentDto(c.getId(), c.getContent(), c.getUser().getNickname(), c.getCreatedDt());
            List<ReplyDto> replies = new ArrayList<>();
            for (Reply r : c.getReplies()) {
                replies.add(new ReplyDto(r.getId(), r.getContent(), r.getUser().getNickname(), r.getCreatedDt()));
            }
            commentDto.setReplies(replies);
            comments.add(commentDto);
        }
        detailFreeBoardDto.setComments(comments);

        return detailFreeBoardDto;
    }

    public boolean checkAuth(Long boardId, User user) {
        Optional<FreeBoard> freeBoard = freeBoardRepository.findFreeBoardWithUser(boardId);
        FreeBoard findFreeBoard = freeBoard.orElseThrow(() -> new BoardNotFoundException("게시판이 존재하지 않습니다."));
        return findFreeBoard.getUser() == user;
    }
}
