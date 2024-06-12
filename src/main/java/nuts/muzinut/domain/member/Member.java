package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.chat.ChatMember;
import nuts.muzinut.domain.chat.Message;
import nuts.muzinut.domain.music.Song;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.domain.music.PlayNut;
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

    //관계 매핑

    //회원 관련
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Mailbox> mailboxes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Follow> followings = new ArrayList<>();

    //음악 관련
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Song> songList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PlayNut> playNutList = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Playlist playlist;

    //게시판 관련
    @OneToMany(mappedBy = "member")
    private List<RecruitBoard> recruitBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<FreeBoard> freeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Lounge> lounges = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Like> likeList = new ArrayList<>();

    //후원 관련
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PaymentHistory> paymentHistories = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<SupportMsg> supportMsgs = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<NutsUsageHistory> nutsUsageHistories = new ArrayList<>();

    //채팅 관련
    @OneToMany(mappedBy = "member")
    private List<ChatMember> chatMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Message> messages = new ArrayList<>();
}
