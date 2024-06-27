package nuts.muzinut.controller.mainpage;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.mainpage.HotSongDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/muzinut")
public class MainPageController {

//    private final HotChartService hotChartService;
//
//    @GetMapping("/hot-chart")
//    public ResponseEntity<List<HotSongDto>> TOP10Song(){
//
//        List<HotSongDto> top10Song = hotChartService.findTOP10Song();
//        return ResponseEntity.ok(top10Song);
//    }

//    @GetMapping("/hot-artist")
//    public ResponseEntity<List<HotArtistDto>> TOP5Artist() {
//
//    }
}
