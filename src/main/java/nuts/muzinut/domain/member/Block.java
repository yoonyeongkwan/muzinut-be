package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
public class Block {

    @Id @GeneratedValue
    @Column(name = "block_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_user_id")
    private User blockUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime blockTime = LocalDateTime.now();


    //연관 관계 메서드
    public void createBlock(User user, User blockUser) {
        this.user = user;
        this.blockUser = blockUser;
        user.getBlocks().add(this);
    }
}
