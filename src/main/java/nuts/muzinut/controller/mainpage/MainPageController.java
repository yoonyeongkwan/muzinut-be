package nuts.muzinut.controller.mainpage;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.mainpage.HotSongDto;
import nuts.muzinut.dto.mainpage.MainTotalDto;
import nuts.muzinut.service.mainpage.MainPageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/muzinut")
public class MainPageController {

    private final MainPageService mainPageService;

    /**
     * 메인 페이지 데이터를 조회하는 엔드포인트입니다.
     *
     * @return 메인 페이지 데이터와 HTTP 상태 코드
     */
    @GetMapping(value = "/main", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainTotalDto> findMainData(){
        return mainPageService.findMainTotalData();
    }


}
