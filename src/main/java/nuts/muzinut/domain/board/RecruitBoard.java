package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recruit_board")
@Getter
public class RecruitBoard extends BaseBoardEntity {

    @Id @GeneratedValue
    @Column(name = "recruit_board_id")
    private Long id;

    /**
     * 탈퇴한 회원이 작성한 게시판 정보는 모두 사라지는가?
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;
    private int recruit_member;
    private LocalDateTime start_duration;
    private LocalDateTime end_duration;
    private LocalDateTime start_work_duration;
    private LocalDateTime end_work_duration;

    @OneToMany(mappedBy = "recruitBoard", cascade = CascadeType.ALL)
    private List<RecruitBoardGenre> recruitBoardGenres = new ArrayList<>();

    //연관 관계 메서드
    public void createRecruitBoard(Member member) {
        this.member = member;
        this.setCreated_dt(LocalDateTime.now());
        this.setModified_dt(LocalDateTime.now());
        member.getRecruitBoards().add(this);
    }
}
