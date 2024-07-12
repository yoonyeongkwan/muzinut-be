package nuts.muzinut.controller.mainpage;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.mainpage.SearchTotalDto;
import nuts.muzinut.service.mainpage.MainSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/muzinut")
public class MainSearchController {

    private final MainSearchService mainSearchService;

    /**
     * 통합 검색을 처리하는 엔드포인트입니다.
     *
     * @param searchWord 검색어
     * @param artistPageable 아티스트 검색 결과 페이지 번호
     * @param songPageable 노래 검색 결과 페이지 번호
     * @return 검색 결과 데이터와 HTTP 상태 코드
     */
    @GetMapping(value = "/{searchword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchTotalDto> findTotalSearch(
            @Validated @PathVariable("searchword") @NotNull String searchWord,
            @RequestParam(name = "artistpage", defaultValue = "1")int artistPageable,
            @RequestParam(name = "songpage", defaultValue = "1")int songPageable
    ){
        return mainSearchService.getTotalSearch(searchWord, artistPageable, songPageable);
    }

    /**
     * 특정 탭(아티스트 또는 노래)에 대한 검색을 처리하는 엔드포인트입니다.
     *
     * @param searchWord 검색어
     * @param tap 검색 대상 탭 (ARTIST 또는 SONG)
     * @param pageable 검색 결과 페이지 번호
     * @return 검색 결과 데이터와 HTTP 상태 코드
     */
    @GetMapping(value = "/{searchword}/{tap}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findSearch(
            @Validated @PathVariable("searchword") @NotNull String searchWord,
            @Validated @PathVariable("tap") @NotNull String tap,
            @RequestParam(name = "page", defaultValue = "1")int pageable
    ){
        if (tap.equalsIgnoreCase("ARTIST")) {
            return mainSearchService.getArtistSearch(searchWord, pageable);
        } else if (tap.equalsIgnoreCase("SONG")) {
            return mainSearchService.getSongSearch(searchWord, pageable);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
