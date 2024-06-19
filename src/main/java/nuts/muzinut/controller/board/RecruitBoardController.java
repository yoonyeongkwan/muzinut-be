package nuts.muzinut.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.board.RecruitBoardDto;
import nuts.muzinut.dto.board.RecruitBoardForm;
import nuts.muzinut.dto.board.SaveRecruitBoardDto;
import nuts.muzinut.exception.BoardNotExistException;
import nuts.muzinut.service.board.RecruitBoardService;
import nuts.muzinut.service.security.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;

    // 모집 게시판 생성 폼을 보여주는 메소드
    @GetMapping("/recruit-boards/new")
    public String newRecruitBoardForm(@ModelAttribute RecruitBoardForm recruitBoardForm) {
        return "board/recruit-board-form";
    }

    // 모집 게시판 생성 요청을 처리하는 메소드
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/recruit-boards/new")
    public ResponseEntity<MessageDto> saveRecruitBoard(@RequestBody RecruitBoardForm recruitBoardForm) throws Exception{

        recruitBoardForm.setUserId(recruitBoardForm.getUserId());

        RecruitBoard recruitBoard = recruitBoardService.saveRecruitBoard(recruitBoardForm);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/recruit-boards/" + recruitBoard.getId())); //생성한 게시판으로 리다이렉트

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(header)
                .body(new MessageDto("모집 게시판이 생성되었습니다")); // 생성된 게시판 상세 페이지로 리디렉션
    }

    /**
     * @param id: recruit board pk
     * @throws: db로 부터 검색되는 엔티티가 없을 경우 NotFoundEntityException 예외 발생 (404 Not found)
     */
    // 특정 모집 게시판을 조회하는 메소드
    @ResponseBody
    @GetMapping("/recruit-boards/{id}")
    public SaveRecruitBoardDto getRecruitBoard(@PathVariable("id") Long id, Model model) {
        RecruitBoard recruitBoard = recruitBoardService.findRecruitBoardById(id);

        return new SaveRecruitBoardDto(
                recruitBoard.getTitle(),
                recruitBoard.getContent(),
                recruitBoard.getView(),
                recruitBoard.getRecruitMember(),
                recruitBoard.getStartDuration(),
                recruitBoard.getEndDuration(),
                recruitBoard.getStartWorkDuration(),
                recruitBoard.getEndWorkDuration(),
                recruitBoard.getGenres()
        );
    }


    // 모든 모집 게시판을 최신 순으로 조회하는 메소드 (페이징 처리)
    @ResponseBody
    @GetMapping("/recruit-boards")
    public RecruitBoardDto getAllRecruitBoards(@RequestParam(value = "page", defaultValue = "0") int page) {
        RecruitBoardDto recruitBoard = recruitBoardService.findAllRecruitBoards(page);
        if (recruitBoard == null) {
            throw new BoardNotExistException("모집 게시판이 존재하지 않습니다.");
        }

        return recruitBoard;
    }

    // 제목을 기준으로 모집 게시판을 검색하는 메소드 (페이징 처리)
    @ResponseBody
    @GetMapping("/recruit-boards/search")
    public Page<SaveRecruitBoardDto> searchRecruitBoardsByTitle(
            @RequestParam("title") String title,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<SaveRecruitBoardDto> recruitBoards = recruitBoardService.searchRecruitBoardsByTitle(title, page, size);
        if (recruitBoards.isEmpty()) {
            throw new BoardNotExistException("모집 게시판이 존재하지 않습니다.");
        }

        return recruitBoards;
    }

    // 조회수를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    @ResponseBody
    @GetMapping("/recruit-boards/view")
    public Page<SaveRecruitBoardDto> getPopularRecruitBoards(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<SaveRecruitBoardDto> recruitBoards = recruitBoardService.findAllRecruitBoardsByView(page, size);
        if (recruitBoards.isEmpty()) {
            throw new BoardNotExistException("모집 게시판이 존재하지 않습니다.");
        }

        return recruitBoards;
    }
//
//    // 장르를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
//    @GetMapping("/recruit-boards/genre")
//    public String getRecruitBoardsByGenre(@RequestParam String genre, Pageable pageable, Model model) {
//        Page<RecruitBoard> recruitBoards = recruitBoardService.findAllRecruitBoardsByGenre(genre, pageable);
//        model.addAttribute("recruitBoards", recruitBoards);
//        return "board/recruit-board-list";
//    }
//
//    // 모집 게시판 수정 폼을 보여주는 메소드
//    @GetMapping("/recruit-boards/{id}/edit")
//    public String editRecruitBoardForm(@PathVariable Long id, Model model) {
//        RecruitBoard recruitBoard = recruitBoardService.findRecruitBoardById(id);
//        model.addAttribute("recruitBoard", recruitBoard);
//        return "board/recruit-board-edit-form";
//    }
//
//    // 모집 게시판 수정을 처리하는 메소드
//    @PostMapping("/recruit-boards/{id}/edit")
//    public String updateRecruitBoard(@ModelAttribute RecruitBoard recruitBoard, RedirectAttributes redirectAttributes) {
//        RecruitBoard updatedBoard = recruitBoardService.updateRecruitBoard(recruitBoard);
//        redirectAttributes.addAttribute("id", updatedBoard.getId());
//        return "redirect:/recruit-boards/{id}";
//    }
//
//    // 모집 게시판 삭제를 처리하는 메소드
//    @PostMapping("/recruit-boards/{id}/delete")
//    public String deleteRecruitBoard(@PathVariable Long id) {
//        recruitBoardService.deleteRecruitBoard(id);
//        return "redirect:/recruit-boards"; // 모집 게시판 목록 페이지로 리디렉션
//    }
}
