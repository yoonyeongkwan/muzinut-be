package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public FreeBoard(String title) {
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
}
