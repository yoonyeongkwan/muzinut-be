package nuts.muzinut;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.dto.security.AuthorityDto;
import nuts.muzinut.repository.member.AuthorityRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.security.UserService;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserService userService;

    @PostConstruct
    public void init() {
        AuthorityDto authorityDto = new AuthorityDto("admin");
        UserDto userDto = new UserDto("admin", "admin", "add");
        userService.signup(userDto);
        UserDto userDto2 = new UserDto("admin1", "admin1", "add");
        userService.signup(userDto2);
    }
}
