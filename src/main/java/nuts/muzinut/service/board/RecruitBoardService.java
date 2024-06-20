package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.board.RecruitBoardDto;
import nuts.muzinut.dto.board.RecruitBoardForm;
import nuts.muzinut.dto.board.RecruitBoardsForm;
import nuts.muzinut.dto.board.SaveRecruitBoardDto;
import nuts.muzinut.exception.BoardNotExistException;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.board.RecruitBoardGenreRepository;
import nuts.muzinut.repository.board.RecruitBoardRepository;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitBoardService {

    private final RecruitBoardRepository recruitBoardRepository;
    private final RecruitBoardGenreRepository recruitBoardGenreRepository;
    private final UserRepository userRepository;

    // 모집 게시판 생성 요청을 처리하는 메소드
    @Transactional
    public RecruitBoard saveRecruitBoard(RecruitBoardForm recruitBoardForm) {
        // 인증된 사용자 정보 가져오기
        String username = getCurrentUsername();
        User user = userRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

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
    public RecruitBoard findRecruitBoardById(Long id) {
        RecruitBoard recruitBoard = recruitBoardRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("모집 게시판이 존재하지 않습니다."));
        recruitBoard.incrementView();
        return recruitBoard;
    }

    // 모든 모집 게시판을 최신 순으로 조회하는 메소드 (페이징 처리)
    public RecruitBoardDto findAllRecruitBoards(int startPage) {
        PageRequest pageRequest = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt"));
        Page<RecruitBoard> page = recruitBoardRepository.findAll(pageRequest);
        if (page.isEmpty()) {
            throw new BoardNotExistException("모집 게시판이 존재하지 않습니다.");
        }
        return convertToRecruitBoardDto(page);
    }

    // 제목을 기준으로 모집 게시판을 검색하는 메소드 (페이징 처리)
    public Page<SaveRecruitBoardDto> searchRecruitBoardsByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RecruitBoard> recruitBoards = recruitBoardRepository.findByTitleContaining(title, pageable);
        if (recruitBoards.isEmpty()) {
            throw new BoardNotExistException("해당 제목의 모집 게시판이 존재하지 않습니다.");
        }
        return convertToSaveRecruitBoardDtoPage(recruitBoards);
    }

    // 조회수를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    public Page<SaveRecruitBoardDto> findAllRecruitBoardsByView(int startPage, int size) {
        PageRequest pageRequest = PageRequest.of(startPage, size, Sort.by(Sort.Direction.DESC, "view"));
        Page<RecruitBoard> recruitBoards = recruitBoardRepository.findAllByOrderByViewDesc(pageRequest);
        if (recruitBoards.isEmpty()) {
            throw new BoardNotExistException("모집 게시판이 존재하지 않습니다.");
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
            throw new BoardNotExistException("모집 게시판이 존재하지 않습니다.");
        }
        return convertToSaveRecruitBoardDtoPage(recruitBoards);
    }

    // 모집 게시판 수정을 처리하는 메소드
    @Transactional
    public RecruitBoard updateRecruitBoard(Long id, RecruitBoardForm recruitBoardForm) throws AccessDeniedException {
        String username = getCurrentUsername();
        RecruitBoard recruitBoard = recruitBoardRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("모집 게시판이 존재하지 않습니다."));

        // 게시글 작성자와 로그인된 사용자가 일치하는지 확인
        if (!recruitBoard.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("해당 게시판을 수정할 권한이 없습니다");
        }

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
        RecruitBoard recruitBoard = recruitBoardRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("해당 ID의 모집 게시판을 찾을 수 없습니다"));

        if (!recruitBoard.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("해당 게시판을 삭제할 권한이 없습니다");
        }
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
}
