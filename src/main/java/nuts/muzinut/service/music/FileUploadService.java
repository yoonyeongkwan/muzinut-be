package nuts.muzinut.service.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class FileUploadService {

    @Value("${spring.file.dir}")
    private String FileUploadDirectory;


    public String albumImgUpload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        // origin 파일 이름 추출
        String originName = file.getOriginalFilename();

        // 파일 이름 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출
        String extension = originName.substring(originName.lastIndexOf("."));

        // uuid와 확장자 결합
        String storeName = uuid + extension;

        String savedir = FileUploadDirectory + "upload/albumImg/";

        // 실제 로컬에 지정된 디렉토리에 저장
        file.transferTo(new File(savedir,storeName));

        return storeName;
    }

    public String songFileUpload(MultipartFile file) throws IOException {

        // origin 파일 이름 추출
        String originName = file.getOriginalFilename();

        // 파일 이름 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출
        String extension = originName.substring(originName.lastIndexOf("."));

        // uuid와 확장자 결합
        String storeName = uuid + extension;

        String savedir = FileUploadDirectory + "upload/songFile/";

        // 실제 로컬에 지정된 디렉토리에 저장
        file.transferTo(new File(savedir, storeName));

        return storeName;
    }

}
