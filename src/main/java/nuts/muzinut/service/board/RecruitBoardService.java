package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.board.RecruitBoardDto;
import nuts.muzinut.dto.board.RecruitBoardForm;
import nuts.muzinut.dto.board.RecruitBoardsForm;
import nuts.muzinut.dto.board.SaveRecruitBoardDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.board.RecruitBoardGenreRepository;
import nuts.muzinut.repository.board.RecruitBoardRepository;
import nuts.muzinut.repository.member.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // User를 조회
        User user = userRepository.findById(recruitBoardForm.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // RecruitBoard 엔티티 생성
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

        // RecruitBoard를 저장
        RecruitBoard savedBoard = recruitBoardRepository.save(recruitBoard);

        // 각 장르를 RecruitBoardGenre 엔티티로 변환하고 저장
        for (String genre : recruitBoardForm.getGenres()) {
            savedBoard.addGenre(genre);
        }
        return savedBoard;
    }

    // 특정 모집 게시판을 조회하는 서비스 메소드
    @Transactional
    public RecruitBoard findRecruitBoardById(Long id) {
        RecruitBoard recruitBoard = recruitBoardRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("모집 게시판이 존재하지 않습니다."));
        recruitBoard.setView(recruitBoard.getView() + 1); // 조회수 증가
        return recruitBoard;
    }

    // 모든 모집 게시판을 최신 순으로 조회하는 메소드 (페이징 처리)
    public RecruitBoardDto findAllRecruitBoards(int startPage) {
        PageRequest pageRequest = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt"));
        Page<RecruitBoard> page = recruitBoardRepository.findAll(pageRequest);
        List<RecruitBoard> recruitBoards = page.getContent();

        if (recruitBoards.isEmpty()) return null;

        RecruitBoardDto boardDto = new RecruitBoardDto();
        for (RecruitBoard recruitBoard : recruitBoards) {
            boardDto.getRecruitBoardsForms().add(new RecruitBoardsForm(
                    recruitBoard.getId(), recruitBoard.getTitle(), recruitBoard.getUser().getId(), recruitBoard.getView(), recruitBoard.getCreatedDt() ));
        }
        return boardDto;
    }

    // 제목을 기준으로 모집 게시판을 검색하는 메소드 (페이징 처리)
    public Page<SaveRecruitBoardDto> searchRecruitBoardsByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // 페이지 크기를 동적으로 설정
        Page<RecruitBoard> recruitBoards = recruitBoardRepository.findByTitleContaining(title, pageable);

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

    // 조회수를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    public Page<SaveRecruitBoardDto> findAllRecruitBoardsByView(int startPage, int size) {
        PageRequest pageRequest = PageRequest.of(startPage, size, Sort.by(Sort.Direction.DESC, "view"));
        Page<RecruitBoard> recruitBoards = recruitBoardRepository.findAllByOrderByViewDesc(pageRequest);

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

    // 장르를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    public Page<RecruitBoard> findAllRecruitBoardsByGenre(String genre, int startPage) {
        PageRequest pageRequest = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        return recruitBoardRepository.findAllByGenre(genre, pageRequest);
    }

    // 모집 게시판 수정을 처리하는 메소드
    @Transactional
    public RecruitBoard updateRecruitBoard(RecruitBoard recruitBoard) {
        return recruitBoardRepository.save(recruitBoard);
    }

    // 모집 게시판 삭제를 처리하는 메소드
    @Transactional
    public void deleteRecruitBoard(Long id) {
        recruitBoardRepository.deleteById(id);
    }
}
