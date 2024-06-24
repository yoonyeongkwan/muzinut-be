package nuts.muzinut.controller.board;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.board.recruit.DetailRecruitBoardDto;
import nuts.muzinut.dto.board.recruit.RecruitBoardDto;
import nuts.muzinut.dto.board.recruit.RecruitBoardForm;
import nuts.muzinut.dto.board.recruit.SaveRecruitBoardDto;
import nuts.muzinut.exception.BoardNotExistException;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.service.board.RecruitBoardService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RecruitBoardController {

    private final RecruitBoardService recruitBoardService;

    // 모집 게시판 생성 폼을 보여주는 메소드
    @GetMapping("/recruit-boards/new")
    public String newRecruitBoardForm(@ModelAttribute RecruitBoardForm recruitBoardForm) {
        return "board/recruit-board-form";
    }

    // 모집 게시판 생성 요청을 처리하는 메소드
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/recruit-boards/new")
    public ResponseEntity<MessageDto> saveRecruitBoard(@RequestBody RecruitBoardForm recruitBoardForm) throws Exception{
        try {
            RecruitBoard recruitBoard = recruitBoardService.saveRecruitBoard(recruitBoardForm);
            HttpHeaders header = new HttpHeaders();
            header.setLocation(URI.create("/recruit-boards/" + recruitBoard.getId())); // 생성한 게시판으로 리다이렉트
            return ResponseEntity.status(HttpStatus.FOUND)
                    .headers(header)
                    .body(new MessageDto("모집 게시판이 생성되었습니다"));
        } catch (Exception e) {
            log.error("모집 게시판 생성 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageDto("모집 게시판 생성 중 오류가 발생하였습니다: " + e.getMessage()));
        }
    }

    // 특정 모집 게시판을 조회하는 메소드
    @ResponseBody
    @GetMapping("/recruit-boards/{id}")
    public ResponseEntity<DetailRecruitBoardDto> getRecruitBoard(@PathVariable("id") Long id) {
        try {
            DetailRecruitBoardDto detail = recruitBoardService.getDetailBoard(id);
            return ResponseEntity.ok(detail);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            log.error("모집 게시판 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // 모든 모집 게시판을 최신 순으로 조회하는 메소드 (페이징 처리)
    @ResponseBody
    @GetMapping("/recruit-boards")
    public ResponseEntity<RecruitBoardDto> getAllRecruitBoards(@RequestParam(value = "page", defaultValue = "0") int page) {
        try {
            RecruitBoardDto recruitBoard = recruitBoardService.findAllRecruitBoards(page);
            return ResponseEntity.ok(recruitBoard);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            log.error("모든 모집 게시판 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // 제목을 기준으로 모집 게시판을 검색하는 메소드 (페이징 처리)
    @ResponseBody
    @GetMapping("/recruit-boards/search")
    public ResponseEntity<Page<SaveRecruitBoardDto>> searchRecruitBoardsByTitle(
            @RequestParam("title") String title,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Page<SaveRecruitBoardDto> recruitBoards = recruitBoardService.searchRecruitBoardsByTitle(title, page, size);
            return ResponseEntity.ok(recruitBoards);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            log.error("모집 게시판 검색 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // 조회수를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    @ResponseBody
    @GetMapping("/recruit-boards/view")
    public ResponseEntity<Page<SaveRecruitBoardDto>> getPopularRecruitBoards(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Page<SaveRecruitBoardDto> recruitBoards = recruitBoardService.findAllRecruitBoardsByView(page, size);
            return ResponseEntity.ok(recruitBoards);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            log.error("조회수 기준 모집 게시판 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // 장르를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    @ResponseBody
    @GetMapping("/recruit-boards/genre")
    public ResponseEntity<Page<SaveRecruitBoardDto>> getRecruitBoardsByGenre(
            @RequestParam("genre") String genre,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Page<SaveRecruitBoardDto> recruitBoards = recruitBoardService.findAllRecruitBoardsByGenre(genre, page, size);
            return ResponseEntity.ok(recruitBoards);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            log.error("장르 기준 모집 게시판 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // 모집 게시판 수정 폼을 보여주는 메소드
    @GetMapping("/recruit-boards/{id}/modify")
    public String modifyRecruitBoardForm(@PathVariable Long id, Model model) {
        try {
            RecruitBoard recruitBoard = recruitBoardService.findRecruitBoardById(id);
            model.addAttribute("recruitBoard", recruitBoard);
            return "board/recruit-board-modify-form";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "모집 게시판을 찾을 수 없습니다.");
            return "error/404";
        } catch (Exception e) {
            log.error("모집 게시판 수정 폼 조회 중 오류 발생", e);
            model.addAttribute("errorMessage", "오류가 발생하였습니다.");
            return "error/500";
        }
    }

    // 모집 게시판 수정을 처리하는 메소드
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/recruit-boards/{id}")
    public ResponseEntity<MessageDto> updateRecruitBoard(@PathVariable("id") Long id, @RequestBody RecruitBoardForm recruitBoardForm) {
        try {
            RecruitBoard updatedBoard = recruitBoardService.updateRecruitBoard(id, recruitBoardForm);

            HttpHeaders header = new HttpHeaders();
            header.setLocation(URI.create("/recruit-boards/" + updatedBoard.getId())); // 수정한 게시판으로 리다이렉트

            return ResponseEntity.status(HttpStatus.FOUND)
                    .headers(header)
                    .body(new MessageDto("모집 게시판이 수정되었습니다")); // 수정된 게시판 상세 페이지로 리디렉션
        } catch (EntityNotFoundException e) {
            log.error("모집 게시판 수정 중 오류 발생: 모집 게시판을 찾을 수 없습니다.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageDto("모집 게시판을 찾을 수 없습니다."));
        } catch (Exception e) {
            log.error("모집 게시판 수정 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageDto("모집 게시판 수정 중 오류가 발생하였습니다: " + e.getMessage()));
        }
    }

    // 모집 게시판 삭제를 처리하는 메소드
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/recruit-boards/{id}")
    public ResponseEntity<MessageDto> deleteRecruitBoard(@PathVariable("id") Long id) {
        try {
            recruitBoardService.deleteRecruitBoard(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new MessageDto("모집 게시판이 삭제되었습니다"));
        } catch (NotFoundEntityException e) {
            log.error("모집 게시판 삭제 중 오류 발생: 모집 게시판을 찾을 수 없습니다.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageDto(e.getMessage()));
        } catch (AccessDeniedException e) {
            log.error("모집 게시판 삭제 중 오류 발생: 접근이 거부되었습니다.", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new MessageDto(e.getMessage()));
        } catch (Exception e) {
            log.error("모집 게시판 삭제 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageDto("모집 게시판 삭제 중 오류가 발생하였습니다: " + e.getMessage()));
        }
    }
}
