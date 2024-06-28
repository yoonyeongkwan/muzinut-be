package nuts.muzinut.service.member;

import java.util.Collections;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.Authority;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.ProfileDto;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.exception.DuplicateMemberException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.repository.member.AuthorityRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.security.SecurityRole;
import nuts.muzinut.service.security.SecurityUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    // 프로필 이미지 설정
    public void setProfileName(String filename, User user) {
        userRepository.updateFilename(filename, user);
    }

    // 프로필 닉네임, 자기소개 설정
    @Transactional
    public void updateNicknameAndIntro(Long userId, String nickname, String intro) {
        userRepository.updateNicknameAndIntro(userId, nickname, intro);
    }

    // 프로필 배너 이미지 설정
    public void setProfileBannerName(String filename, User user){
        userRepository.updateProfileBannerImg(filename, user);
    }


    /**
     * 일반 회원가입
     * @throws : 이미 가입된 회원이 동일한 username 으로 회원가입 한 경우 DuplicateMemberException 발생
     */
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        //권한 설정
        Authority authority = getAuthority(SecurityRole.ROLE_USER);

        //유저 생성
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }


    /**
     * 관리자 회원가입
     * @throws : 이미 가입된 회원이 동일한 username 으로 회원가입 한 경우 DuplicateMemberException 발생
     */
    public UserDto adminSignup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        //권한 설정
        Authority authority = getAuthority(SecurityRole.ROLE_ADMIN);

        //유저 생성
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    private Authority getAuthority(SecurityRole role) {
        Authority authority = Authority.builder()
                .authorityName(role.toString())
                .build();
        authorityRepository.save(authority);
        return authority;
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    /**
     * 토큰을 토대로 로그인 한 사용자인지 검증
     * @throws NotFoundMemberException: 스프링 시큐리티가 관리하지 않는 회원일 경우 exception 발생
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserWithUsername() {
        return userRepository.findByUsername(
                SecurityUtil.getCurrentUsername()
                        .orElseThrow(() -> new NotFoundMemberException("로그인 하지 않았거나 없는 회원입니다")));
    }

    // 토큰의 사용자 이름 반환
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Member not found"));
    }

}
