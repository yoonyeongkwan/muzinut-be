package nuts.muzinut;

import io.jsonwebtoken.io.IOException;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.dto.security.AuthorityDto;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.AlbumRepository;
import nuts.muzinut.repository.music.SongRepository;
import nuts.muzinut.service.member.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInit {


    private final UserService userService;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    @Value("${spring.file.dir}")
    private String fileDir;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() throws IOException {
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




    }
//    @PostConstruct
//    public void albuminit() throws FileNotFoundException {
//        Optional<User> userId = userRepository.findById(1L);
//        User user = userId.get();
//        Album album = new Album(user,"안녕","이렇게좋은날","testImage1.png");
//        albumRepository.save(album);
//        for (int j = 0; j < 4; j++){
//            String songFilename = "song" + (j+1) + ".mp3";
//            // User user, String title, String lyrics, String lyricist, String composer, String Filename, Album album
//            Song song = new Song(user,"좋은날","가사입니다","아이유","아이유", songFilename,album);
//            songRepository.save(song);
//        }
//    }


}
