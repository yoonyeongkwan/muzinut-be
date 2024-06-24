package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "recruit_board")
@Getter
public class RecruitBoard extends Board {

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;

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

    // 새로운 생성자
    public RecruitBoard(User user, String content, int recruitMember, LocalDateTime startDuration, LocalDateTime endDuration, LocalDateTime startWorkDuration, LocalDateTime endWorkDuration, String title) {
        this.content = content;
        this.recruitMember = recruitMember;
        this.startDuration = startDuration;
        this.endDuration = endDuration;
        this.startWorkDuration = startWorkDuration;
        this.endWorkDuration = endWorkDuration;
        super.title = title;
//        user.getRecruitBoards().add(this);
        super.user = user; // Board 클래스의 user 필드를 설정
        List<RecruitBoard> recruitBoards = user.getRecruitBoards();
        if (recruitBoards != null) {
            recruitBoards.add(this);
        }
    }

    // 장르 추가 메서드
    public void addGenre(String genre) {
        RecruitBoardGenre recruitBoardGenre = new RecruitBoardGenre();
        recruitBoardGenre.setGenre(genre);
        recruitBoardGenre.addRecruitGenre(this);
        this.recruitBoardGenres.add(recruitBoardGenre);
    }

    // 장르 삭제 메서드
    public void clearGenres() {
        this.recruitBoardGenres.clear();
    }

    // 장르 리스트를 문자열 리스트로 변환하는 메서드
    public List<String> getGenres() {
        return recruitBoardGenres.stream()
                .map(RecruitBoardGenre::getGenre)
                .collect(Collectors.toList());
    }

    // 조회수 증가 메서드
    public void incrementView() {
        this.view++;
    }

    // 수정 메서드
    public void update(User user, String content, int recruitMember, LocalDateTime startDuration,
                   LocalDateTime endDuration, LocalDateTime startWorkDuration, LocalDateTime endWorkDuration, String title) {
        this.content = content;
        this.recruitMember = recruitMember;
        this.startDuration = startDuration;
        this.endDuration = endDuration;
        this.startWorkDuration = startWorkDuration;
        this.endWorkDuration = endWorkDuration;
        this.title = title;
        super.user = user; // Board 클래스의 user 필드를 설정
    }
}
