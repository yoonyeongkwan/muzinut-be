package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.User;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "free_board")
public class FreeBoard extends Board {

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    private String filename; //본문 내용이 담긴 html 파일 (react.quails)

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public FreeBoard(String title) {
        super.title = title;
    }

    public int addView() {
        return ++this.view;
    }
}
