package nuts.muzinut.controller.music;

import lombok.AllArgsConstructor;
import nuts.muzinut.service.music.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/muzinut")
@AllArgsConstructor
public class UploadController {

    private final FileUploadService fileUploadService;
//    private final UploadService uploadService;
//
//    @PostMapping("/album-upload")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public ResponseEntity createMusic(
//            @RequestPart(name = "album") AlbumDto albumDTO,
//            @RequestPart(name = "albumImg") MultipartFile albumImg,
//            @RequestPart(name = "songs") List<SongDto> songDtos,
//            @RequestPart(name = "musicFile") List<MultipartFile> songFiles
//    ) {
//        AlbumDto createdMusic = uploadService.createMusic(albumDTO, albumImg, songDtos, songFiles);
//        try {
//
//        }catch(IOException e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createdMusic);
//        }
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdMusic);
//    }

//    @PostMapping(value = "/uploadtest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity uploadTest(@RequestPart(name = "albumImg") MultipartFile albumImg) {
//        System.out.println("albumImg = " + albumImg.getClass());
//        try {
//            fileUploadService.albumImgUpload(albumImg);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(albumImg);
//        }
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(albumImg);
//    }
}
