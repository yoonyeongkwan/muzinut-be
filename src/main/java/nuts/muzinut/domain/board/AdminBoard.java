package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.User;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "admin_board")
public class AdminBoard extends BaseBoardEntity {

    @Id @GeneratedValue
    @Column(name = "admin_board_id")
    private Long id;

    private String title;
    private String content;
    private int view;

    public AdminBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @OneToMany(mappedBy = "adminBoard", cascade = CascadeType.ALL)
    private List<AdminUploadFile> adminUploadFiles = new ArrayList<>();

}
