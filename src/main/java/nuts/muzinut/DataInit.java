package nuts.muzinut;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.dto.security.AuthorityDto;
import nuts.muzinut.repository.board.CommentRepository;
import nuts.muzinut.repository.board.LikeRepository;
import nuts.muzinut.repository.board.ReplyRepository;
import nuts.muzinut.repository.board.*;
import nuts.muzinut.service.DataInitService;
import nuts.muzinut.service.member.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final AdminBoardRepository adminBoardRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final ReplyRepository replyRepository;
    private final DataInitService dataInitService;
    private final FreeBoardRepository freeBoardRepository;
    private final UserService userService;

    @PersistenceContext
    EntityManager em;

//    @PostConstruct
//    public void init() {
//        dataInitService.initializeData();
//    }

//    @PostConstruct
//    public void init() {
//        AuthorityDto authorityDto = new AuthorityDto("admin");
//        UserDto userDto = new UserDto("admin@naver.com", "admin", "add!");
//        userService.adminSignup(userDto);
//        UserDto userDto2 = new UserDto("user@naver.com", "user", "user!");
//        userService.signup(userDto2);
////        UserDto userDto3 = new UserDto("user2@naver.com", "user2", "user2!");
////        userService.signup(userDto3);
//
////        dataInitService.recruitBoardBoardScenario();
//    }



    @PostConstruct
    public void init() {
        AuthorityDto authorityDto = new AuthorityDto("admin");
        UserDto userDto = new UserDto("admin@naver.com", "admin", "아이유");
        userService.adminSignup(userDto);
        UserDto userDto2 = new UserDto("user@naver.com", "user", "신용재");
        userService.signup(userDto2);
        UserDto userDto3 = new UserDto("user2@naver.com", "user2", "허각");
        userService.signup(userDto3);
        UserDto userDto4 = new UserDto("admin2@naver.com", "admin2", "이수");
        userService.adminSignup(userDto4);
        UserDto userDto5 = new UserDto("user3@naver.com", "user3", "FT아이랜드");
        userService.signup(userDto5);
        UserDto userDto6 = new UserDto("user4@naver.com", "user4", "김범수");
        userService.signup(userDto6);
        UserDto userDto7 = new UserDto("admin3@naver.com", "admin3", "장덕철");
        userService.adminSignup(userDto7);
        UserDto userDto8 = new UserDto("user5@naver.com", "user5", "바이브");
        userService.signup(userDto8);
        UserDto userDto9 = new UserDto("user6@naver.com", "user6", "노을");
        userService.signup(userDto9);
        UserDto userDto10 = new UserDto("admin4@naver.com", "admin4", "먼데이키즈");
        userService.adminSignup(userDto10);
        UserDto userDto11 = new UserDto("user7@naver.com", "user7", "마크툽");
        userService.signup(userDto11);
        UserDto userDto12 = new UserDto("user8@naver.com", "user8", "임한별");
        userService.signup(userDto12);


//        dataInitService.recruitBoardBoardScenario();
//        dataInitService.commentScenario();
    }



}
