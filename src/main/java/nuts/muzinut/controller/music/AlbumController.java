package nuts.muzinut.controller.music;


import nuts.muzinut.dto.music.AlbumDto;
import nuts.muzinut.exception.AlbumCreateFailException;
import nuts.muzinut.service.music.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    // 앨범 업로드
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Long>> uploadAlbum(
            @RequestPart("albumImg") MultipartFile albumImg,
            @RequestPart("songFiles") List<MultipartFile> songFiles,
            @RequestPart("albumData") AlbumDto albumData
    ) throws IOException {
        System.out.println("===============메소드 실행============");
        // 앨범 이미지 저장
        String storeAlbumImg = albumService.saveAlbumImg(albumImg);
        // 각 곡들 저장
        AlbumDto storeAlbumData = albumService.saveSongs(songFiles, albumData);
        // 엔티티 저장
        Long albumId = albumService.saveAlbumData(storeAlbumData, storeAlbumImg);
        if(albumId == null) throw new AlbumCreateFailException("앨범 등록에 실패하였습니다. (Entity Create Error)");
        Map<String, Long> body = new HashMap<>();
        body.put("albumId", albumId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(body);
    }

    // 앱범 수정
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    @PutMapping("/modify/{albumid}")
//    public ResponseEntity<String> updateAlbum(
//            @PathVariable("albumid") Long albumId,
//            @RequestPart("albumImg") MultipartFile albumImg,
//            @RequestPart("songFiles") List<MultipartFile> songFiles,
//            @RequestPart("albumData") AlbumDto albumData
//    ) throws IOException {
//
//    }
}