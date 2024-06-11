package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "admin_board")
public class AdminBoard extends BaseBoardEntity {

    @Id @GeneratedValue
    @Column(name = "admin_board_id")
    private Long id;

    private String content;

    @Column(name = "admin_id")
    private Long adminId;

    @OneToMany(mappedBy = "adminBoard", cascade = CascadeType.ALL)
    private List<AdminUploadFile> adminUploadFiles = new ArrayList<>();
}
