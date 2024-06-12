package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "admin_upload_file")
public class AdminUploadFile {

    @Id @GeneratedValue
    @Column(name = "admin_upload_file_id")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_board_id")
    private AdminBoard adminBoard;

    @Column(name = "store_file_name")
    private String storeFilename;

    @Column(name = "origin_file_name")
    private String originFilename;

    //연관 관계 메서드
    public void addFiles(AdminBoard adminBoard) {
        this.adminBoard = adminBoard;
        adminBoard.getAdminUploadFiles().add(this);
    }
}
