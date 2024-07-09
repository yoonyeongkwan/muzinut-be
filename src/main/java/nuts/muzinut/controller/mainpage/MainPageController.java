package nuts.muzinut.controller.mainpage;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.mainpage.HotSongDto;
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

    @GetMapping(value = "/main", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> findMainData(){
        MultiValueMap<String, Object> data = mainPageService.findMainTotalData();
        return new ResponseEntity<MultiValueMap<String, Object>>(data, HttpStatus.OK);
    }


}
