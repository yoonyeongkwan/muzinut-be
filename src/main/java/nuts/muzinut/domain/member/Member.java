package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String nickname;
    private String intro;

    private Integer nuts;
    private String role;
    private int declaration;
    private String profile_img_filename;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Mailbox> mailboxes = new ArrayList<>();
}
