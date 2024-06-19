package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.AdminUploadFile;
import nuts.muzinut.dto.board.AdminBoardsDto;
import nuts.muzinut.dto.board.AdminBoardsForm;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.board.AdminUploadFileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBoardService {

    private final AdminBoardRepository adminBoardRepository;
    private final AdminUploadFileRepository uploadFileRepository;

    public AdminBoard saveWithFile(AdminUploadFile adminUploadFile) {
        AdminBoard adminBoard = new AdminBoard();
        adminUploadFile.addFiles(adminBoard);
        return adminBoardRepository.save(adminBoard);
    }

    public AdminBoardsDto getAdminBoards(int startPage) {
        PageRequest pageRequest = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt")); //Todo 한 페이지에 가져올 게시판 수를 정하기
        Page<AdminBoard> page = adminBoardRepository.findAll(pageRequest);
        List<AdminBoard> adminBoards = page.getContent();

        if (adminBoards.isEmpty()) {
            return null;
        }

        AdminBoardsDto boardsDto = new AdminBoardsDto();
        for (AdminBoard adminBoard : adminBoards) {
            boardsDto.getAdminBoardsForms().add(new AdminBoardsForm(adminBoard.getId(), adminBoard.getTitle(), "muzi", adminBoard.getView(), adminBoard.getCreatedDt()));
        }
        return boardsDto;
    }
}
