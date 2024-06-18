package nuts.muzinut.service.board;

import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.board.RecruitBoardGenre;
import nuts.muzinut.repository.board.RecruitBoardGenreRepository;
import nuts.muzinut.repository.board.RecruitBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RecruitBoardService {

    @Autowired
    private RecruitBoardRepository recruitBoardRepository;

    @Autowired
    private RecruitBoardGenreRepository recruitBoardGenreRepository;

    // 모집 게시판 생성
    @Transactional
    public RecruitBoard createRecruitBoard(RecruitBoard recruitBoard, List<String> genres) {
        RecruitBoard savedBoard = recruitBoardRepository.save(recruitBoard);
        for (String genre : genres) {
            RecruitBoardGenre boardGenre = new RecruitBoardGenre();
            boardGenre.setGenre(genre);
            boardGenre.addRecruitGenre(savedBoard); // addRecruitGenre 메서드를 사용하여 연관 관계 설정
            recruitBoardGenreRepository.save(boardGenre);
        }
        return savedBoard;
    }

    // 특정 모집 게시판 조회
    public RecruitBoard findRecruitBoardById(Long id) {
        return recruitBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid recruit board ID"));
    }

    // 모든 모집 게시판 조회 (페이징)
    public Page<RecruitBoard> findAllRecruitBoards(Pageable pageable) {
        return recruitBoardRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    // 제목에 부합하는 모집 게시판 검색
    public Page<RecruitBoard> searchRecruitBoardsByTitle(String title, Pageable pageable) {
        return recruitBoardRepository.findByTitleContaining(title, pageable);
    }

    // 조회수 순서대로 모집 게시판 조회
    public Page<RecruitBoard> findAllRecruitBoardsByView(Pageable pageable) {
        return recruitBoardRepository.findAllByOrderByViewDesc(pageable);
    }

    // 모집 장르에 따른 게시판 조회
    public Page<RecruitBoard> findAllRecruitBoardsByGenre(String genre, Pageable pageable) {
        return recruitBoardRepository.findAllByGenre(genre, pageable);
    }

    // 모집 게시판 수정
    @Transactional
    public RecruitBoard updateRecruitBoard(RecruitBoard recruitBoard) {
        return recruitBoardRepository.save(recruitBoard);
    }

    // 모집 게시판 삭제
    @Transactional
    public void deleteRecruitBoard(Long id) {
        recruitBoardRepository.deleteById(id);
    }
}
