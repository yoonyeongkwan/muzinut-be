package nuts.muzinut.controller.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.service.board.RecruitBoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/recruit-boards")
@RequiredArgsConstructor
public class RecruitBoardController {

    private final RecruitBoardService recruitBoardService;

    // 모집 게시판 생성 폼을 보여주는 메소드
    @GetMapping("/new")
    public String newRecruitBoardForm(@ModelAttribute RecruitBoard recruitBoard) {
        return "board/recruit-board-form";
    }

    // 모집 게시판 생성 요청을 처리하는 메소드
    @PostMapping("/new")
    public String createRecruitBoard(@ModelAttribute RecruitBoard recruitBoard,
                                     @RequestParam List<String> genres,
                                     RedirectAttributes redirectAttributes) {
        RecruitBoard savedBoard = recruitBoardService.createRecruitBoard(recruitBoard, genres);
        redirectAttributes.addAttribute("id", savedBoard.getId());
        return "redirect:/recruit-boards/{id}"; // 생성된 게시판 상세 페이지로 리디렉션
    }

    // 특정 모집 게시판을 조회하는 메소드
    @GetMapping("/{id}")
    public String getRecruitBoard(@PathVariable Long id, Model model) {
        RecruitBoard recruitBoard = recruitBoardService.findRecruitBoardById(id);
        model.addAttribute("recruitBoard", recruitBoard);
        return "board/recruit-board"; // 특정 모집 게시판 뷰 반환
    }

    // 모든 모집 게시판을 최신 순으로 조회하는 메소드 (페이징 처리)
    @GetMapping
    public String getAllRecruitBoards(Pageable pageable, Model model) {
        Page<RecruitBoard> recruitBoards = recruitBoardService.findAllRecruitBoards(pageable);
        model.addAttribute("recruitBoards", recruitBoards);
        return "board/recruit-board-list";
    }

    // 제목을 기준으로 모집 게시판을 검색하는 메소드 (페이징 처리)
    @GetMapping("/search")
    public String searchRecruitBoardsByTitle(@RequestParam String title, Pageable pageable, Model model) {
        Page<RecruitBoard> recruitBoards = recruitBoardService.searchRecruitBoardsByTitle(title, pageable);
        model.addAttribute("recruitBoards", recruitBoards);
        return "board/recruit-board-list";
    }

    // 조회수를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    @GetMapping("/view")
    public String getPopularRecruitBoards(Pageable pageable, Model model) {
        Page<RecruitBoard> recruitBoards = recruitBoardService.findAllRecruitBoardsByView(pageable);
        model.addAttribute("recruitBoards", recruitBoards);
        return "board/recruit-board-list";
    }

    // 장르를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    @GetMapping("/genre")
    public String getRecruitBoardsByGenre(@RequestParam String genre, Pageable pageable, Model model) {
        Page<RecruitBoard> recruitBoards = recruitBoardService.findAllRecruitBoardsByGenre(genre, pageable);
        model.addAttribute("recruitBoards", recruitBoards);
        return "board/recruit-board-list";
    }

    // 모집 게시판 수정 폼을 보여주는 메소드
    @GetMapping("/{id}/edit")
    public String editRecruitBoardForm(@PathVariable Long id, Model model) {
        RecruitBoard recruitBoard = recruitBoardService.findRecruitBoardById(id);
        model.addAttribute("recruitBoard", recruitBoard);
        return "board/recruit-board-edit-form";
    }

    // 모집 게시판 수정을 처리하는 메소드
    @PostMapping("/{id}/edit")
    public String updateRecruitBoard(@ModelAttribute RecruitBoard recruitBoard, RedirectAttributes redirectAttributes) {
        RecruitBoard updatedBoard = recruitBoardService.updateRecruitBoard(recruitBoard);
        redirectAttributes.addAttribute("id", updatedBoard.getId());
        return "redirect:/recruit-boards/{id}";
    }

    // 모집 게시판 삭제를 처리하는 메소드
    @PostMapping("/{id}/delete")
    public String deleteRecruitBoard(@PathVariable Long id) {
        recruitBoardService.deleteRecruitBoard(id);
        return "redirect:/recruit-boards"; // 모집 게시판 목록 페이지로 리디렉션
    }
}
