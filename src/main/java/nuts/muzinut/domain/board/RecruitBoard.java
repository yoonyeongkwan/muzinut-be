package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.User;

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
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @Column(name = "recruit_member")
    private int recruitMember;

    @Column(name = "start_duration")
    private LocalDateTime startDuration;

    @Column(name = "end_duration")
    private LocalDateTime endDuration;

    @Column(name = "start_work_duration")
    private LocalDateTime startWorkDuration;

    @Column(name = "end_work_duration")
    private LocalDateTime endWorkDuration;

    @OneToMany(mappedBy = "recruitBoard", cascade = CascadeType.ALL)
    private List<RecruitBoardGenre> recruitBoardGenres = new ArrayList<>();

    // 기본 생성자
    public RecruitBoard() {
    }

    //연관 관계 메서드
    public void createRecruitBoard(User user) {
        this.user = user;
        user.getRecruitBoards().add(this);
    }

    // 새로운 생성자
    public RecruitBoard(User user, String content, int recruitMember, LocalDateTime startDuration, LocalDateTime endDuration, LocalDateTime startWorkDuration, LocalDateTime endWorkDuration) {
        this.user = user;
        this.content = content;
        this.recruitMember = recruitMember;
        this.startDuration = startDuration;
        this.endDuration = endDuration;
        this.startWorkDuration = startWorkDuration;
        this.endWorkDuration = endWorkDuration;
        user.getRecruitBoards().add(this);
    }

    // 장르 추가 메서드
    public void addGenre(String genre) {
        RecruitBoardGenre recruitBoardGenre = new RecruitBoardGenre();
        recruitBoardGenre.setGenre(genre);
        recruitBoardGenre.addRecruitGenre(this);
        this.recruitBoardGenres.add(recruitBoardGenre);
    }
}
