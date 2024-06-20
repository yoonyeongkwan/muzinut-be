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
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.board.AdminUploadFileRepository;
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
    private final BoardQueryRepository boardQueryRepository;
    private final AdminUploadFileRepository uploadFileRepository;

    public AdminBoard saveWithFile(AdminUploadFile adminUploadFile) {
        AdminBoard adminBoard = new AdminBoard();
        adminUploadFile.addFiles(adminBoard);
        return adminBoardRepository.save(adminBoard);
    }

    public AdminBoardsDto getAdminBoards(int startPage) {
        PageRequest pageRequest = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt")); //Todo 한 페이지에 가져올 게시판 수를 정하기
        Page<AdminBoard> page = adminBoardRepository.findAll(pageRequest);
        List<AdminBoard> adminBoards = page.getContent();

        if (adminBoards.isEmpty()) {
            return null;
        }

        AdminBoardsDto boardsDto = new AdminBoardsDto();
        for (AdminBoard adminBoard : adminBoards) {
            boardsDto.getAdminBoardsForms().add(new AdminBoardsForm(adminBoard.getId(), adminBoard.getTitle(), "muzi", adminBoard.getView(), adminBoard.getCreatedDt()));
        }
        return boardsDto;
    }

    /**
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
