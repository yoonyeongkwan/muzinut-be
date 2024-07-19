package nuts.muzinut.dto.member.profile.Lounge;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProfileLoungesForm {
    private Long id;
    private String writer;
    private String filename;
    private LocalDateTime createdDt;
    private int like;
    private int view;
    private String content; // 파일의 내용을 저장할 필드 추가

    public void setContent(String content) {
        this.content = content;
    }

    public ProfileLoungesForm(Long id, String writer, String filename, LocalDateTime createdDt, int like, int view) {
        this.id = id;
        this.writer = writer;
        this.filename = filename;
        this.createdDt = createdDt;
        this.like = like;
        this.view = view;
    }
}
