package nuts.muzinut.controller.music;


import jakarta.validation.constraints.NotNull;
import nuts.muzinut.dto.music.AlbumDetaillResultDto;
import nuts.muzinut.dto.music.AlbumDto;
import nuts.muzinut.dto.music.AlbumUpdateDto;
import nuts.muzinut.service.music.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    // 앨범 업로드
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadAlbum(
            @RequestPart("albumImg") MultipartFile albumImg,
            @RequestPart("songFiles") List<MultipartFile> songFiles,
            @RequestPart("albumData") AlbumDto albumData
    ) throws IOException {
        // 앨범 이미지 저장
        String storeAlbumImg = albumService.saveAlbumImg(albumImg);
        // 각 곡들 저장
        AlbumDto storeAlbumData = albumService.saveSongs(songFiles, albumData);
        // 엔티티 저장
        albumService.saveAlbumData(storeAlbumData, storeAlbumImg);

        return ResponseEntity.status(HttpStatus.OK).body("Files uploaded successfully!");
    }

    /**
     * 앨범 수정을 가져오는 엔드포인트
     *
     * @param albumId 앨범의 ID
     * @param albumImg 앨범 이미지 파일
     * @param albumData 앨범의 정보
     * @return 앨범 상세 정보와 HTTP 상태 코드
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAlbum(
            @PathVariable("id") Long albumId,
            @RequestPart("albumImg") MultipartFile albumImg,
            @RequestPart("albumData") AlbumUpdateDto albumData
    ) {
        albumService.updateAlbum(albumId, albumImg, albumData);
        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/album/" + albumId)); //수정한 앨범디테일패이지로 리다이렉트

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .headers(header)
                .body("앨범 수정 완료 되었습니다");
    }

    /**
     * 앨범 상세 정보를 가져오는 엔드포인트
     *
     * @param albumId 앨범의 ID
     * @return 앨범 상세 정보와 HTTP 상태 코드
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumDetaillResultDto> getSongDetail
    (@Validated @PathVariable("id") @NotNull Long albumId) {
        return albumService.getAlbumDetail(albumId);
    }

    /**
     * 앨범 삭제를 가져오는 엔드포인트
     *
     * @param albumId 앨범의 ID
     * @return 앨범 상세 정보와 HTTP 상태 코드
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAlbum(@Validated @PathVariable("id") @NotNull Long albumId){
        albumService.albumDelete(albumId);
        return new ResponseEntity<String>("앨범 삭제가 완료 되었습니다", HttpStatus.OK);
    }

}