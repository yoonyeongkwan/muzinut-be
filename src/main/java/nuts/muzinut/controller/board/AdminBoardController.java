package nuts.muzinut.controller.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.AdminUploadFile;
import nuts.muzinut.dto.board.AdminBoardForm;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.board.AdminUploadFileRepository;
import nuts.muzinut.service.board.AdminBoardService;
import nuts.muzinut.service.board.FileStore;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;


@Controller
//@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBoardController {

    private final AdminBoardRepository adminBoardRepository;
    private final AdminUploadFileRepository uploadFileRepository;
    private final FileStore fileStore;
    private final AdminBoardService adminBoardService;

    @GetMapping("/admin-boards/new")
    public String newAdminBoard(@ModelAttribute AdminBoardForm form) {
        return "/board/admin-board-form";
    }

    @PostMapping("/admin-boards/new")
    public String saveAdminBoard(@ModelAttribute AdminBoardForm form, RedirectAttributes redirectAttributes) throws IOException {

        AdminBoard adminBoard = new AdminBoard();

//        AdminUploadFile attachFile = fileStore.storeFile(form.getAttachFile(), adminBoard); //첨부파일을 저장
        List<AdminUploadFile> adminUploadFiles = fileStore.storeFiles(form.getAttachFiles(), adminBoard); //첨부 파일들을 저장

//        redirectAttributes.addAttribute("adminBoardId", 1L); //테스트를 위해
//        return "redirect:/admin-boards/{adminBoardId}";
        return "/board/admin-test";
    }

    /**
     *
     * @param id: admin board pk
     * @throws: 엔티티
     * @return
     */
    @GetMapping("/admin-boards/{id}")
    public String adminBoards(@PathVariable Long id, Model model) {
        Optional<AdminBoard> adminBoard = adminBoardRepository.findById(id);
        AdminBoard findAdminBoard = adminBoard.orElseThrow(() -> new NotFoundEntityException("어드민 게시판이 존재하지 않습니다."));
        List<AdminUploadFile> adminUploadFiles = findAdminBoard.getAdminUploadFiles();

        model.addAttribute("adminBoard", findAdminBoard);
        model.addAttribute("uploadFiles", adminUploadFiles);
        return "/board/admin-board";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }
}
