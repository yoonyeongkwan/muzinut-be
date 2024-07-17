package nuts.muzinut.controller.music;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.music.*;
import nuts.muzinut.service.music.PlayNutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class PlayNutController {

    private final PlayNutService playNutService;

    /**
     * 플리넛의 목록을 가져오는 엔드포인트.
     * @return 플리넛의 데이터와 HTTP 상태 코드
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/playnut", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayNutListDto> getPlayNutDir() {
        return playNutService.findPlayNutDir();
    }

    /**
     * 플리넛의 곡 목록을 가져오는 엔드포인트.
     *
     * @param playNutId 플리넛의 ID
     * @return 플리넛 곡 데이터와 HTTP 상태 코드
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/playnut/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayNutMusicListDto> getPlayNutMusic(@Validated @PathVariable("id") @NotNull Long playNutId) {
        return playNutService.findPlayNutMusic(playNutId);
    }

    /**
     * 플리넛을 생성하는 엔드포인트.
     * @param data 플리넛의 제목
     * @return 플리넛의 생성 성공 메시지와 HTTP 상태 코드
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/playnut")
    public ResponseEntity<String> savePlayNutDir(@RequestBody PlaynutTitleDto data){
        playNutService.savePlayNut(data);
        return new ResponseEntity<String>("플리넛 생성되었습니다", HttpStatus.CREATED);
    }

    /**
     * 플리넛의 이름 변경하는 엔드포인트.
     * @param data 플리넛의 이름 변경할 데이터
     * @param playNutId 플리넛의 ID
     * @return 플리넛의 이름변경과 HTTP 상태 코드
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/playnut/{id}")
    public ResponseEntity<String> updatePlayNut(
            @Validated @PathVariable("id") @NotNull Long playNutId,
            @RequestBody PlaynutTitleDto data) {
        playNutService.updatePlayNut(playNutId, data);
        return new ResponseEntity<String>("플리넛 이름이 변경되었습니다", HttpStatus.OK);
    }

    /**
     * 플리넛 삭제하는 엔드포인트.
     * @param playNutId 플리넛의 ID
     * @return 플리넛 삭제 메시지와 HTTP 상태 코드
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/playnut/{id}")
    public ResponseEntity<String> DeletePlayNut(@Validated @PathVariable("id") @NotNull Long playNutId) {
        playNutService.playNutDelete(playNutId);
        return new ResponseEntity<String>("플리넛 삭제 완료되었습니다", HttpStatus.OK);
    }

    /**
     * 플리넛의 곡을 추가하는 엔드포인트.
     * @param playNutId 플리넛의 ID
     * @param songId 곡의 ID
     * @return 플리넛 곡 추가와 HTTP 상태 코드
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/playnut/{playnutid}/{songId}")
    public ResponseEntity<String> savePlayNutMusic
            (@Validated @PathVariable("playnutid") @NotNull Long playNutId,
             @Validated @PathVariable("songId") @NotNull Long songId){
        playNutService.saveMusic(playNutId, songId);
        return new ResponseEntity<String>("플리넛 곡 추가 완료되었습니다", HttpStatus.CREATED);
    }

    /**
     * 플리넛의 곡을 삭제하는 엔드포인트.
     * @param playNutMusicId 플리넛 곡의 ID
     * @return 플리넛 곡 삭제 메시지와 HTTP 상태 코드
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/playnut/{playnutid}/{playnutMusicid}")
    public ResponseEntity<String> DeletePlayMusic
            (@Validated @PathVariable("playnutid") @NotNull Long playNutId,
             @Validated @PathVariable("playnutMusicid") @NotNull Long playNutMusicId) {
        playNutService.playNutMusicDelete(playNutId, playNutMusicId);
        return new ResponseEntity<String>("플리넛 곡 삭제 완료되었습니다", HttpStatus.OK);
    }
}
