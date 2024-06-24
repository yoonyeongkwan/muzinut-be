package nuts.muzinut.service.board;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.dto.board.admin.AdminBoardsDto;
import nuts.muzinut.dto.board.admin.AdminBoardsForm;
import nuts.muzinut.dto.board.admin.DetailAdminBoardDto;
import nuts.muzinut.dto.board.comment.CommentDto;
import nuts.muzinut.dto.board.comment.ReplyDto;
import nuts.muzinut.exception.BoardNotExistException;
import nuts.muzinut.exception.BoardNotFoundException;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundFileException;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.board.AdminUploadFileRepository;
import nuts.muzinut.repository.board.BoardRepository;
import nuts.muzinut.repository.board.query.BoardQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static nuts.muzinut.domain.board.QBoard.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminBoardService {

    private final AdminBoardRepository adminBoardRepository;
    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final AdminUploadFileRepository uploadFileRepository;

    public AdminBoard saveWithFile(AdminUploadFile adminUploadFile) {
        AdminBoard adminBoard = new AdminBoard();
        adminUploadFile.addFiles(adminBoard);
        return adminBoardRepository.save(adminBoard);
    }

    public AdminBoard getAdminBoard(Long id) throws BoardNotExistException {
        return adminBoardRepository.findById(id).orElseThrow(
                () -> new BoardNotExistException("어드민 게시판이 없습니다"));
    }

    /**
     * 모든 어드민 게시판 리스트를 가져오는 메서드
     * @param startPage: 시작 페이지를 넘겨주면 그에 해당하는 데이터들을 가져온다.
     * @return
     */
    public AdminBoardsDto getAdminBoards(int startPage) throws BoardNotExistException {
        PageRequest pageRequest = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt")); //Todo 한 페이지에 가져올 게시판 수를 정하기
        Page<AdminBoard> page = adminBoardRepository.findAll(pageRequest);
        List<AdminBoard> adminBoards = page.getContent();

        if (adminBoards.isEmpty()) {
            throw new BoardNotExistException("어드민 게시판이 존재하지 않습니다.");
        }

        AdminBoardsDto boardsDto = new AdminBoardsDto();
        boardsDto.setPaging(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        for (AdminBoard adminBoard : adminBoards) {
            boardsDto.getAdminBoardsForms().add(new AdminBoardsForm(adminBoard.getId(), adminBoard.getTitle(), "muzi", adminBoard.getView(), adminBoard.getCreatedDt()));
        }
        return boardsDto;
    }

    //특정 게시판의 삭제
    public void deleteAdminBoard(Long id) {
        boardRepository.deleteById(id);
    }

    /**
     * 특정 어드민 게시판의 다운로드 받고자 하는 첨부파일이 어떤것이 있는지 리턴
     * @param id: 어드민 게시판 pk
     * @return: 첨부 파일
     * @throws: 다운로드 받을 파일이 없으면 NotFoundEntityException 발생
     */
    public AdminUploadFile getAttachFile(Long id) {
        Optional<AdminUploadFile> file = uploadFileRepository.findById(id);
        return file.orElseThrow(() -> new NotFoundFileException("다운로드를 받고자 하는 파일이 없습니다"));
    }

    /**
     * 특정 어드민 게시판의 정보를 가져와 dto 로 변환하는 메서드
     * tuple element (adminBoard, commentDto, replyDto, like.count)
     * @param boardId: adminBoard pk
     * @return: dto
     */
    public DetailAdminBoardDto getDetailAdminBoard(Long boardId) {
        List<Tuple> result = boardQueryRepository.getDetailBoard(boardId);

        if (result.isEmpty()) {
            return null;
        }


        Tuple first = result.getFirst();
        Board findBoard = first.get(board);
        AdminBoard findAdminBoard = adminBoardRepository.findById(findBoard.getId())
                .orElseThrow(() -> new NotFoundEntityException("adminBoard Not exist"));

        if (findBoard == null) {
            return null;
        }

        List<AdminUploadFile> files = uploadFileRepository.getAdminUploadFile(findAdminBoard.getId());
        DetailAdminBoardDto detailAdminBoardDto =
                new DetailAdminBoardDto(findBoard.getTitle(), findAdminBoard.getContent(),
                        findAdminBoard.getView(), files); //어드민 게시판 관련 파일 셋팅

        Long likeCount = first.get(3, Long.class);
        detailAdminBoardDto.setLikeCount(likeCount); //좋아요 수 셋팅

        Set<CommentDto> commentDtoSet = new HashSet<>();
        Set<ReplyDto> replyDtoSet = new HashSet<>();

        //data Setting
        for (Tuple t : result) {
            ReplyDto findReply = t.get(2, ReplyDto.class);
            CommentDto findComment = t.get(1, CommentDto.class);

            if (findComment.getId() != null) {
                commentDtoSet.add(findComment);
            }

            if (findReply.getId() != null) {
                replyDtoSet.add(findReply);
            }
        }

//        log.info("commentDtoSet size: {}", commentDtoSet.size());
//        log.info("replyDtoSet size: {}", replyDtoSet.size());

        //detailAdminBoardDto comments setting
        List<CommentDto> comments = new ArrayList<>(commentDtoSet);
        for (ReplyDto replyDto : replyDtoSet) {

            for (CommentDto comment : comments) {
                if (comment.getId() == replyDto.getCommentId()) {
                    comment.getReplies().add(replyDto);
                }
            }
        }
        detailAdminBoardDto.setComments(comments);

        return detailAdminBoardDto;
    }
}
