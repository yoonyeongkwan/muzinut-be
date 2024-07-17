package nuts.muzinut.controller.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.board.recruit.DetailRecruitBoardDto;
import nuts.muzinut.dto.board.recruit.RecruitBoardDto;
import nuts.muzinut.dto.board.recruit.RecruitBoardForm;
import nuts.muzinut.dto.board.recruit.SaveRecruitBoardDto;
import nuts.muzinut.exception.BoardNotExistException;
import nuts.muzinut.exception.BoardNotFoundException;
import nuts.muzinut.service.board.FileStore;
import nuts.muzinut.service.board.RecruitBoardService;
import nuts.muzinut.service.member.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class RecruitBoardController {

    private final RecruitBoardService recruitBoardService;
    private final FileStore fileStore;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    // 모집 게시판 생성 폼을 보여주는 메소드
    @GetMapping("/recruit-boards/new")
    public String newRecruitBoardForm(@ModelAttribute RecruitBoardForm recruitBoardForm) {
        return "community/board/recruit-board-form";
    }

    // 모집 게시판 생성 요청을 처리하는 메소드
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/recruit-boards/new")
    public ResponseEntity<MessageDto> saveRecruitBoard(@RequestBody RecruitBoardForm recruitBoardForm) throws Exception {
        RecruitBoard recruitBoard = recruitBoardService.saveRecruitBoard(recruitBoardForm);
        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/community/recruit-boards/" + recruitBoard.getId())); // 생성한 게시판으로 리다이렉트
        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(header)
                .body(new MessageDto("모집 게시판이 생성되었습니다"));
    }

    // 특정 모집 게시판을 조회하는 메소드
    @ResponseBody
    @GetMapping(value = "/recruit-boards/{id}")
    public ResponseEntity<DetailRecruitBoardDto> getRecruitBoard(@PathVariable("id") Long id) throws JsonProcessingException {
        //회원이 보는 상세페이지 인지, 비회원이 보는 상세페이지인지 구분
        User findUser = userService.getUserWithUsername().orElse(null);

        DetailRecruitBoardDto detailRecruitBoardDto = recruitBoardService.getDetailRecruitBoard(id, findUser);

        if (detailRecruitBoardDto == null) {
            throw new BoardNotFoundException("해당 게시판이 존재하지 않습니다");
        }

        String jsonString = objectMapper.writeValueAsString(detailRecruitBoardDto);

        return new ResponseEntity<DetailRecruitBoardDto>(detailRecruitBoardDto, HttpStatus.OK);
    }

    // 모든 모집 게시판을 최신 순으로 조회하는 메소드 (페이징 처리)
    @ResponseBody
    @GetMapping("/recruit-boards")
    public ResponseEntity<RecruitBoardDto> getAllRecruitBoards(@RequestParam(value = "page", defaultValue = "0") int page) {
        RecruitBoardDto recruitBoard = recruitBoardService.findAllRecruitBoards(page);
        if (recruitBoard == null) {
            throw new BoardNotExistException("모집 게시판이 존재하지 않습니다.");
        }
        return ResponseEntity.ok(recruitBoard);
    }

    // 제목을 기준으로 모집 게시판을 검색하는 메소드 (페이징 처리)
    @ResponseBody
    @GetMapping("/recruit-boards/search")
    public ResponseEntity<Page<SaveRecruitBoardDto>> searchRecruitBoardsByTitle(
            @RequestParam("title") String title,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) throws BoardNotExistException {
        Page<SaveRecruitBoardDto> recruitBoards = recruitBoardService.searchRecruitBoardsByTitle(title, page, size);
        if (recruitBoards.isEmpty()) {
            throw new BoardNotExistException("모집 게시판이 존재하지 않습니다.");
        }
        return ResponseEntity.ok(recruitBoards);
    }


    // 조회수를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    @ResponseBody
    @GetMapping("/recruit-boards/view")
    public ResponseEntity<Page<SaveRecruitBoardDto>> getPopularRecruitBoards(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<SaveRecruitBoardDto> recruitBoards = recruitBoardService.findAllRecruitBoardsByView(page, size);
        return ResponseEntity.ok(recruitBoards);
    }

    // 장르를 기준으로 모집 게시판을 조회하는 메소드 (페이징 처리)
    @ResponseBody
    @GetMapping("/recruit-boards/genre")
    public ResponseEntity<Page<SaveRecruitBoardDto>> getRecruitBoardsByGenre(
            @RequestParam("genre") String genre,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<SaveRecruitBoardDto> recruitBoards = recruitBoardService.findAllRecruitBoardsByGenre(genre, page, size);
        if (recruitBoards.isEmpty()) {
            throw new BoardNotExistException("모집 게시판이 존재하지 않습니다.");
        }
        return ResponseEntity.ok(recruitBoards);
    }

    // 모집 게시판 수정 폼을 보여주는 메소드
    @GetMapping("/recruit-boards/{id}/modify")
    public String modifyRecruitBoardForm(@PathVariable Long id, Model model) {
        RecruitBoard recruitBoard = recruitBoardService.findRecruitBoardById(id);
        model.addAttribute("recruitBoard", recruitBoard);
        return "board/recruit-board-modify-form";
    }

    // 모집 게시판 수정을 처리하는 메소드
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/recruit-boards/{id}")
    public ResponseEntity<MessageDto> updateRecruitBoard(@PathVariable("id") Long id, @RequestBody RecruitBoardForm recruitBoardForm) {
        RecruitBoard updatedBoard = recruitBoardService.updateRecruitBoard(id, recruitBoardForm);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/community/recruit-boards/" + updatedBoard.getId())); // 수정한 게시판으로 리다이렉트

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(header)
                .body(new MessageDto("모집 게시판이 수정되었습니다")); // 수정된 게시판 상세 페이지로 리디렉션
    }

    // 모집 게시판 삭제를 처리하는 메소드
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/recruit-boards/{id}")
    public ResponseEntity<MessageDto> deleteRecruitBoard(@PathVariable("id") Long id) {
        recruitBoardService.deleteRecruitBoard(id);
        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/community/recruit-boards")); // 모든 게시판 조회 페이지로 리다이렉트
        return ResponseEntity.status(HttpStatus.OK)
                .headers(header)
                .body(new MessageDto("모집 게시판이 삭제되었습니다"));
    }
}