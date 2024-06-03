package nuts.muzinut.domain.board;

import jakarta.persistence.*;

@Entity
@Table(name = "recruit_board_genre")
public class RecruitBoardGenre {

    @Id @GeneratedValue
    @Column(name = "recruit_board_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_board_id")
    private RecruitBoard recruitBoard;
    private String genre;

    //연관 관계 메서드
    public void addRecruitGenre(RecruitBoard recruitBoard) {
        this.recruitBoard = recruitBoard;
        recruitBoard.getRecruitBoardGenres().add(this);
    }
}
