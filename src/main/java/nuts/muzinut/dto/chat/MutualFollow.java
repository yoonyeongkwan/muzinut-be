package nuts.muzinut.dto.chat;

import lombok.Data;

@Data
public class MutualFollow {

    private Long id;
    private String username;
    private String nickname;
    private String profileImgFilename; // 프로필 이미지 파일명 추가
}
