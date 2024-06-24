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
public class AdminBoard extends Board {

    private String content;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;

    public AdminBoard(String title, String content) {
        super.title = title;
        this.content = content;
    }

    public Long getId() {
        return super.getId();
    }

    @OneToMany(mappedBy = "adminBoard", cascade = CascadeType.ALL)
    private List<AdminUploadFile> adminUploadFiles = new ArrayList<>();
//
//    @OneToMany(mappedBy = "adminBoard", cascade = CascadeType.ALL)
//    private List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "adminBoard", cascade = CascadeType.ALL)
//    private List<Like> likes = new ArrayList<>();
}
