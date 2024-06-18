package nuts.muzinut.service.board;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.AdminUploadFile;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.board.AdminUploadFileRepository;
import org.springframework.stereotype.Service;

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
}
