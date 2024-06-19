package nuts.muzinut;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.dto.security.AuthorityDto;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.member.AuthorityRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.security.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserService userService;
    private final AdminBoardRepository adminBoardRepository;

    @PostConstruct
    public void init() {
        AuthorityDto authorityDto = new AuthorityDto("admin");
        UserDto userDto = new UserDto("admin@naver.com", "admin", "add!");
        userService.adminSignup(userDto);
        UserDto userDto2 = new UserDto("user@naver.com", "user", "user!");
        userService.signup(userDto2);

        createAdminBoards();
    }

    private void createAdminBoards() {
        for (int i = 0; i < 20; i++) {
            AdminBoard adminBoard = new AdminBoard();
            adminBoardRepository.save(adminBoard);
        }
    }
}
