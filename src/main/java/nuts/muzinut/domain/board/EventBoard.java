package nuts.muzinut.domain.board;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "event_board")
public class EventBoard extends Board {


    private String filename; //본문 내용이 담긴 html 파일 (react.quails)
    private String img;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public EventBoard(String title) {
        super.title = title;
    }

    public int addView() {
        return ++this.view;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void update(String title, String filename) {
        this.title = title;
        this.filename = filename;
    }

    public void update(String title, String filename, String img) {
        this.title = title;
        this.filename = filename;
        this.img = img;
    }
}
