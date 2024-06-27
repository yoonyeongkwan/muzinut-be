package nuts.muzinut.service.board;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.board.comment.CommentDto;
import nuts.muzinut.dto.board.comment.ReplyDto;
import nuts.muzinut.dto.board.free.DetailFreeBoardDto;
import nuts.muzinut.dto.board.free.FreeBoardsDto;
import nuts.muzinut.dto.board.free.FreeBoardsForm;
import nuts.muzinut.dto.board.lounge.DetailLoungeDto;
import nuts.muzinut.dto.board.lounge.LoungesDto;
import nuts.muzinut.dto.board.lounge.LoungesForm;
import nuts.muzinut.exception.BoardNotExistException;
import nuts.muzinut.exception.BoardNotFoundException;
import nuts.muzinut.repository.board.BoardRepository;
import nuts.muzinut.repository.board.LoungeRepository;
import nuts.muzinut.repository.board.query.FreeBoardQueryRepository;
import nuts.muzinut.repository.board.query.LoungeQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QFreeBoard.freeBoard;
import static nuts.muzinut.domain.board.QLounge.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoungeService {

//    private final FreeBoardRepository freeBoardRepository;
    private final LoungeRepository loungeRepository;
    private final BoardRepository boardRepository;
//    private final FreeBoardQueryRepository queryRepository;
    private final LoungeQueryRepository queryRepository;

    public Lounge save(Lounge lounge) {
        return loungeRepository.save(lounge);
    }

    /**
     * @throws BoardNotFoundException: 찾고자 하는 자유 게시판이 없는 경우 404
     * @return: FreeBoard 엔티티
     */
    public Lounge getLounge(Long id) {
        return loungeRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("찾고자 하는 라운지 게시판이 없습니다"));
    }

    public void deleteLounge(Long id) {
        loungeRepository.deleteById(id);
    }

    public void updateLounge(Long id, String filename) {
        loungeRepository.updateLounge(filename, id);
    }

    //Todo 모든 라운지 조회
    public LoungesDto getLounges(int startPage) throws BoardNotExistException {
        PageRequest pageRequest = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt")); //Todo 한 페이지에 가져올 게시판 수를 정하기
        Page<Lounge> page = loungeRepository.findAll(pageRequest);
        List<Lounge> lounges = page.getContent();

        if (lounges.isEmpty()) {
            throw new BoardNotExistException("라운지 게시판이 없습니다.");
        }

        LoungesDto loungesDto = new LoungesDto();
        loungesDto.setPaging(page.getNumber(), page.getTotalPages(), page.getTotalElements()); //paging 처리
        for (Lounge l : lounges) {
            loungesDto.getLoungesForms().add(new LoungesForm(l.getId(), l.getUser().getNickname(), l.getFilename(),
                    l.getCreatedDt(), l.getLikes().size(), l.getView()));
        }
        return loungesDto;
    }

    /**
     * 특정 라운지 게시판 조회
     * tuple (board, lounge, like.count)
     */
    public DetailLoungeDto detailLounge(Long boardId) {
        List<Tuple> result = queryRepository.getDetailLounge(boardId);

        log.info("tuple: {}", result);
        if (result.isEmpty()) {
            throw new BoardNotFoundException("라운지 게시판이 존재하지 않습니다.");
        }

        Tuple first = result.getFirst();
        Board findBoard = first.get(board);
        Lounge findLounge = first.get(lounge);
        int view = findLounge.addView();

        if (findBoard == null || findLounge == null) {
            return null;
        }

        DetailLoungeDto detailLoungeDto = new DetailLoungeDto(findLounge.getId(), findLounge.getUser().getNickname(),
                view ,findLounge.getFilename());

        Long likeCount = first.get(2, Long.class);
        detailLoungeDto.setLikeCount(likeCount); //좋아요 수 셋팅

        //댓글 및 대댓글 dto 에 셋팅
        List<CommentDto> comments = new ArrayList<>();
        for (Comment c : findBoard.getComments()) {
            CommentDto commentDto = new CommentDto(c.getId(), c.getContent(), c.getUser().getNickname(), c.getCreatedDt(), c.getUser().getProfileImgFilename());
            List<ReplyDto> replies = new ArrayList<>();
            for (Reply r : c.getReplies()) {
                replies.add(new ReplyDto(r.getId(), r.getContent(), r.getUser().getNickname(), r.getCreatedDt(), r.getUser().getProfileImgFilename()));
            }
            commentDto.setReplies(replies);
            comments.add(commentDto);
        }
        detailLoungeDto.setComments(comments);

        return detailLoungeDto;
    }

    public boolean checkAuth(Long boardId, User user) {
        Optional<Lounge> findLounge = loungeRepository.findLoungeWithUser(boardId);
        Lounge lounge = findLounge.orElseThrow(() -> new BoardNotFoundException("게시판이 존재하지 않습니다."));
        return lounge.getUser() == user;
    }

    public List<String> getProfileImages(DetailLoungeDto detailLoungeDto) {

        List<String> profileImages = new ArrayList<>();
        addWriterProfile(profileImages, detailLoungeDto.getProfileImg()); //게시판 작성자의 프로필 추가

        for (CommentDto c : detailLoungeDto.getComments()) {
            addWriterProfile(profileImages, c.getCommentProfileImg()); //댓글 작성자의 프로필 추가

            for (ReplyDto r : c.getReplies()) {
                addWriterProfile(profileImages, r.getReplyProfileImg()); //대댓글 작성자의 프로필 추가
            }
        }
        return profileImages;
    }

    private void addWriterProfile(List<String> profileImages, String profileImg) {
        if (StringUtils.hasText(profileImg)) {
            profileImages.add(profileImg);
        }
    }
}
