package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "admin_board")
public class AdminBoard extends Board {

    private String filename; //react quill file

    public int addView() {
        return ++this.view;
    }

    public AdminBoard(String title) {
        super.title = title;
    }

    public Long getId() {
        return super.getId();
    }

    public void changeFilename(String filename) {
        this.filename = filename;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "adminBoard", cascade = CascadeType.ALL)
    private List<AdminUploadFile> adminUploadFiles = new ArrayList<>();
}
