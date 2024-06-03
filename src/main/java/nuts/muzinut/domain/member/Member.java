package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.board.Bookmark;
import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.Lounge;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.music.Music;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.domain.music.PleNut;
import nuts.muzinut.domain.nuts.NutsUsageHistory;
import nuts.muzinut.domain.nuts.PaymentHistory;
import nuts.muzinut.domain.nuts.SupportMsg;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String nickname; //별명
    private String intro; //자기 소개
    private Integer nuts; //보유 너츠
    private int vote; //투표권
    private int declaration; //신고 횟수

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role; //admin, artist...

    @Column(name = "profile_img_filename")
    private String profileImgFilename;

    @Column(name = "account_number")
    private int accountNumber; //계좌 번호

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Mailbox> mailboxes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<NutsUsageHistory> nutsUsageHistories = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Music> musicList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PleNut> pleNut = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Playlist playlist;

    @OneToMany(mappedBy = "member")
    private List<RecruitBoard> recruitBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Lounge> lounges = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PaymentHistory> paymentHistories = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<SupportMsg> supportMsgs = new ArrayList<>();
}
