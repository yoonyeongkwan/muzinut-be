package nuts.muzinut.controller.board;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nuts.muzinut.dto.board.board.BoardSearchForm;
import nuts.muzinut.dto.board.board.BoardsDto;
import nuts.muzinut.service.board.BoardService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/boards")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시판 검색기능
     * @param form: 제목을 담는 dto
     * @param searchCond: 검색 조건 (이벤트, 자유, 어드민...)
     * @param page: 시작 페이지
     */
    @PostMapping
    public BoardsDto search(@RequestBody @Validated BoardSearchForm form,
                            @Validated @NotBlank @RequestParam String searchCond,
                            @RequestParam(value = "page", defaultValue = "0") int page) {
        return boardService.searchResult(form.getTitle(), searchCond, page);
    }
}
