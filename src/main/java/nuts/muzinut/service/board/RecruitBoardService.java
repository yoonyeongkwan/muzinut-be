
package nuts.muzinut.service.board;

import com.querydsl.core.Tuple;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.board.comment.CommentDto;
import nuts.muzinut.dto.board.comment.ReplyDto;
import nuts.muzinut.dto.board.recruit.*;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.board.RecruitBoardGenreRepository;
import nuts.muzinut.repository.board.RecruitBoardRepository;
import nuts.muzinut.repository.board.query.BoardQueryRepository;
import nuts.muzinut.repository.member.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitBoardService {

    private final RecruitBoardRepository recruitBoardRepository;
    private final RecruitBoardGenreRepository recruitBoardGenreRepository;
    private final UserRepository userRepository;
    private final BoardQueryRepository boardQueryRepository;

    // 모집 게시판 생성 요청을 처리하는 메소드
    @Transactional
    public RecruitBoard saveRecruitBoard(RecruitBoardForm recruitBoardForm) {
        // 인증된 사용자 정보 가져오기
        String username = getCurrentUsername();
        User user = userRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Invalid user"));

        RecruitBoard recruitBoard = new RecruitBoard(
                user,
                recruitBoardForm.getContent(),
                recruitBoardForm.getRecruitMember(),
                recruitBoardForm.getStartDuration(),
                recruitBoardForm.getEndDuration(),
                recruitBoardForm.getStartWorkDuration(),
                recruitBoardForm.getEndWorkDuration(),
                recruitBoardForm.getTitle()
        );
        RecruitBoard savedBoard = recruitBoardRepository.save(recruitBoard);
        // 각 장르를 RecruitBoardGenre 엔티티로 변환하고 저장
        for (String genre : recruitBoardForm.getGenres()) {
            savedBoard.addGenre(genre);
        }
        return savedBoard;
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

    // 특정 모집 게시판을 조회하는 서비스 메소드
    @Transactional
    public DetailRecruitBoardDto getDetailBoard(Long id) {
        RecruitBoard recruitBoard = checkEntityExists(id);
        recruitBoard.incrementView();

        // 작성자 정보 가져오기
        String author = recruitBoard.getUser().getNickname();

        // 댓글과 대댓글 가져오기
        List<Tuple> result = boardQueryRepository.getDetailBoard(id);

        List<CommentDto> commentDtoList = new ArrayList<>();
        Set<CommentDto> commentDtoSet = new HashSet<>();
        Set<ReplyDto> replyDtoSet = new HashSet<>();

        if (!result.isEmpty()) {
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

            List<CommentDto> comments = new ArrayList<>(commentDtoSet);
            for (ReplyDto replyDto : replyDtoSet) {
                for (CommentDto comment : comments) {
                    if (comment.getId().equals(replyDto.getCommentId())) {
                        comment.getReplies().add(replyDto);
                    }
                }
            }
            commentDtoList = comments;
        }

        DetailRecruitBoardDto detailRecruitBoardDto = new DetailRecruitBoardDto(
                recruitBoard.getTitle(),
                recruitBoard.getContent(),
                recruitBoard.getView(),
                recruitBoard.getRecruitMember(),
                recruitBoard.getStartDuration(),
                recruitBoard.getEndDuration(),
                recruitBoard.getStartWorkDuration(),
                recruitBoard.getEndWorkDuration(),
                recruitBoard.getGenres(),
                author,
                commentDtoList
        );

        return detailRecruitBoardDto;
    }


    // 모든 모집 게시판을 최신 순으로 조회하는 메소드 (페이징 처리)
    public RecruitBoardDto findAllRecruitBoards(int startPage) {
        PageRequest pageRequest = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt"));
        Page<RecruitBoard> page = recruitBoardRepository.findAll(pageRequest);
        if (page.isEmpty()) {
            throw new EntityNotFoundException("모집 게시판이 존재하지 않습니다.");
        }
        return convertToRecruitBoardDto(page);
    }

    // 제목을 기준으로 모집 게시판을 검색하는 메소드 (페이징 처리)
    public Page<SaveRecruitBoardDto> searchRecruitBoardsByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RecruitBoard> recruitBoards = recruitBoardRepository.findByTitleContaining(title, pageable);
        if (recruitBoards.isEmpty()) {
            throw new EntityNotFoundException("해당 제목의 모집 게시판이 존재하지 않습니다.");
        }
        return convertToSaveRecruitBoardDtoPage(recruitBoards);
    }

    // 조회수를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    public Page<SaveRecruitBoardDto> findAllRecruitBoardsByView(int startPage, int size) {
        PageRequest pageRequest = PageRequest.of(startPage, size, Sort.by(Sort.Direction.DESC, "view"));
        Page<RecruitBoard> recruitBoards = recruitBoardRepository.findAllByOrderByViewDesc(pageRequest);
        if (recruitBoards.isEmpty()) {
            throw new EntityNotFoundException("모집 게시판이 존재하지 않습니다.");
        }
        return convertToSaveRecruitBoardDtoPage(recruitBoards);
    }

    // 장르를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    public Page<SaveRecruitBoardDto> findAllRecruitBoardsByGenre(String genre, int page, int size) {
        if (!recruitBoardGenreRepository.existsByGenre(genre)) {
            throw new NotFoundEntityException("존재하지 않는 장르입니다.");
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDt"));
        Page<RecruitBoard> recruitBoards = recruitBoardRepository.findAllByGenre(genre, pageRequest);

        if (recruitBoards.isEmpty()) {
            throw new EntityNotFoundException("모집 게시판이 존재하지 않습니다.");
        }
        return convertToSaveRecruitBoardDtoPage(recruitBoards);
    }

    // 모집 게시판 수정 폼을 보여주는 메소드
    @Transactional
    public RecruitBoard findRecruitBoardById(Long id) {
        RecruitBoard recruitBoard = checkEntityExists(id);
        recruitBoard.incrementView();
        return recruitBoard;
    }

    // 모집 게시판 수정을 처리하는 메소드
    @Transactional
    public RecruitBoard updateRecruitBoard(Long id, RecruitBoardForm recruitBoardForm) throws AccessDeniedException {
        String username = getCurrentUsername();
        RecruitBoard recruitBoard = checkEntityExists(id);
        checkUserAuthorization(recruitBoard, username);

        // RecruitBoard 엔티티 업데이트
        recruitBoard.update(
                recruitBoard.getUser(),
                recruitBoardForm.getContent(),
                recruitBoardForm.getRecruitMember(),
                recruitBoardForm.getStartDuration(),
                recruitBoardForm.getEndDuration(),
                recruitBoardForm.getStartWorkDuration(),
                recruitBoardForm.getEndWorkDuration(),
                recruitBoardForm.getTitle()
        );

        // 기존 장르 리스트를 지우고 새로 추가
        recruitBoard.clearGenres();
        recruitBoardGenreRepository.deleteByRecruitBoardId(id);
        for (String genre : recruitBoardForm.getGenres()) {
            recruitBoard.addGenre(genre);
        }

        return recruitBoardRepository.save(recruitBoard);
    }

    // 모집 게시판 삭제를 처리하는 메소드
    @Transactional
    public void deleteRecruitBoard(Long id) throws AccessDeniedException {
        String username = getCurrentUsername();
        RecruitBoard recruitBoard = checkEntityExists(id);
        checkUserAuthorization(recruitBoard, username);
        recruitBoardRepository.delete(recruitBoard);
    }

    // Page<RecruitBoard>를 RecruitBoardDto로 변환하는 메소드
    private RecruitBoardDto convertToRecruitBoardDto(Page<RecruitBoard> page) {
        List<RecruitBoard> recruitBoards = page.getContent();
        if (recruitBoards.isEmpty()) return null;

        RecruitBoardDto boardDto = new RecruitBoardDto();
        for (RecruitBoard recruitBoard : recruitBoards) {
            boardDto.getRecruitBoardsForms().add(new RecruitBoardsForm(
                    recruitBoard.getId(), recruitBoard.getTitle(), recruitBoard.getUser().getId(), recruitBoard.getView(), recruitBoard.getCreatedDt()
            ));
        }
        return boardDto;
    }

    // Page<RecruitBoard>를 Page<SaveRecruitBoardDto>로 변환하는 메소드
    private Page<SaveRecruitBoardDto> convertToSaveRecruitBoardDtoPage(Page<RecruitBoard> recruitBoards) {
        return recruitBoards.map(board -> new SaveRecruitBoardDto(
                board.getTitle(),
                board.getContent(),
                board.getView(),
                board.getRecruitMember(),
                board.getStartDuration(),
                board.getEndDuration(),
                board.getStartWorkDuration(),
                board.getEndWorkDuration(),
                board.getGenres()
        ));
    }

    // 공통 예외 처리를 위한 메소드
    private RecruitBoard checkEntityExists(Long id) {
        return recruitBoardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 모집 게시판을 찾을 수 없습니다"));
    }

    // 공통 예외 처리를 위한 메소드
    private void checkUserAuthorization(RecruitBoard recruitBoard, String username) throws AccessDeniedException {
        if (!recruitBoard.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("해당 게시판에 대한 권한이 없습니다");
        }
    }
}
