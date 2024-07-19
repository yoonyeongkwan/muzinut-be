package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.User;

@Entity
@Getter
public class Lounge extends Board {

//    private String filename; //react quill file

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int addView() {
        return ++this.view;
    }
}
