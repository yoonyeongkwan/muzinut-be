package nuts.muzinut.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class Base64Encoding {

    @Value("${spring.file.dir}")
    private String fileDir;

    @Value("${spring.file.profile-base-img}")
    private String profileBaseImg;

    @Value("${spring.file.banner-base-img}")
    private String bannerBaseImg;

    //사용자 프로필 이미지명을 가져와서 base64로 인코딩 하여 반환하는 메서드
    public String encodeFileToBase64(String filename){
        File file;
        try {
            if (StringUtils.hasText(filename)) {
                file = new File(fileDir + filename); //설정된 프로필 이미지
            } else {
                file = new File(profileBaseImg); //기본 프로필 이미지
            }
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);

        } catch (IOException e) {
            return null;
        }
    }
}
