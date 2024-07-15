package nuts.muzinut.controller.music;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.music.SongDetaillResultDto;
import nuts.muzinut.dto.music.SongPageDto;
import nuts.muzinut.dto.music.SongUpdateDto;
import nuts.muzinut.dto.page.PageDto;
import nuts.muzinut.service.music.SongService;
import nuts.muzinut.service.music.TestPlayViewService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/music")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private final TestPlayViewService testPlayViewService;
    @PostMapping("/test/{id}")
    public ResponseEntity<String> testPlayView(@PathVariable("id") Long id){
        testPlayViewService.TestPlayView(id);
        return ResponseEntity.ok("성공");
    }

    /**
     * 최신 노래 목록을 가져오는 엔드포인트.
     *
     * @param pageable 페이지 번호 (기본값 1)
     * @return 최신 노래 데이터와 HTTP 상태 코드
     */
    @GetMapping(value = "/newsong", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageDto<SongPageDto>> getNewSongs(@RequestParam(name = "page", defaultValue = "1")int pageable) {

        return songService.getNewSongs(pageable);
    }

    /**
     * 가장 인기 있는 상위 100곡을 가져오는 엔드포인트.
     *
     * @param pageable 페이지 번호 (기본값 1)
     * @return 상위 100곡 데이터와 HTTP 상태 코드
     */
    @GetMapping(value = "/hotsong", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageDto<SongPageDto>> getTOP100Songs(@RequestParam(name = "page", defaultValue = "1")int pageable) {

        return songService.getHotTOP100Songs(pageable);
    }

    /**
     * 특정 장르의 노래 목록을 가져오는 엔드포인트.
     *
     * @param genre    장르명
     * @param pageable 페이지 번호 (기본값 1)
     * @return 장르별 노래 데이터와 HTTP 상태 코드
     */
    @GetMapping(value = "/genresong/{genre}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageDto<SongPageDto>> getGenreSongs
            (@PathVariable("genre")String genre,
             @RequestParam(name = "page", defaultValue = "1")int pageable)
    {
        return songService.getGenreSongs(pageable, genre);
    }

    /**
     * 특정 노래의 상세 정보를 가져오는 엔드포인트.
     *
     * @param songId 노래의 ID
     * @return 노래 상세 정보와 HTTP 상태 코드
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SongDetaillResultDto> getSongDetail
            (@Validated @PathVariable("id") @NotNull Long songId){
        return songService.getSongDetail(songId);
    }

    /**
     * 곡 정보를 수정하는 엔드포인트.
     *
     * @param songId 노래의 ID
     * @param songFile  
     * @return 노래 상세 정보와 HTTP 상태 코드
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSong(
            @Validated @PathVariable("id") @NotNull Long songId,
            @RequestPart("songFile")MultipartFile songFile,
            @RequestPart("songData")SongUpdateDto songData){
        songService.updateSong(songId, songData, songFile);
        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/music/" + songId)); //수정한 음원디테일패이지로 리다이렉트

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .headers(header)
                .body("음원 수정 완료 되었습니다");
    }

    /**
     * 곡 삭제를 가져오는 엔드포인트.
     *
     * @param songId 노래의 ID
     * @return 노래 상세 정보와 HTTP 상태 코드
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSong(@Validated @PathVariable("id") @NotNull Long songId) {
        songService.songDelete(songId);
        return new ResponseEntity<String>("음원 삭제가 완료 되었습니다", HttpStatus.OK);
    }
}
