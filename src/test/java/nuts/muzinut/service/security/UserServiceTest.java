package nuts.muzinut.service.security;

import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.Authority;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.exception.DuplicateMemberException;
import nuts.muzinut.exception.InvalidPasswordException;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.member.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    void signUp() {

        //given
        UserDto userDto = new UserDto("admin", "admin", "add");

        //when
        userService.signup(userDto);

        //then
        Optional<User> findUser = userRepository.findByNickname("add");
        assertThat(findUser.get().getPassword().equals("admin")).isFalse(); //비밀번호가 암호화 됨

        List<Authority> list = findUser.get().getAuthorities().stream().toList();
        assertThat(list.get(0).getAuthorityName()).isEqualTo("ROLE_USER"); //회원 가입시 역할은 USER (security 용도)
    }

    //이미 회원가입한 회원이 한번더 회원가입하면 예외가 발생한다
    @Test
    void duplicateSignUp() {

        //given
        UserDto userDto = new UserDto("admin", "admin", "add");

        //when
        userService.signup(userDto);

        //then (동일한 username 으로 로그인 시 예외 발생)
        assertThatThrownBy(() -> userService.signup(userDto))
                .isInstanceOf(DuplicateMemberException.class);
    }

    @Test
    void changePassword() {

        //given
        UserDto userDto = new UserDto("admin", "admin", "add");
        userService.signup(userDto);
        User findUser = userRepository.findAll().getFirst();

        //when
        userService.updatePassword(findUser, "admin", "admin!");

        //then

    }

    @Test
    void changePasswordWithWrongCurrentPassword() {

        //given
        UserDto userDto = new UserDto("admin", "admin", "add");
        userService.signup(userDto);

        //when
        User findUser = userRepository.findAll().getFirst();

        //then
        assertThatThrownBy(() ->
                userService.updatePassword(findUser, "admin~", "admin!"))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void randomNickname() {
        String r = userService.randomNickname().substring(0, 10);
        log.info("랜덤 닉네임: {}", r);
    }
}