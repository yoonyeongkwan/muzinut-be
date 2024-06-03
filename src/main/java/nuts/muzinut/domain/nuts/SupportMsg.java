package nuts.muzinut.domain.nuts;

import jakarta.persistence.*;
import nuts.muzinut.domain.member.Member;

@Entity
@Table(name = "support_msg")
public class SupportMsg {

    @Id @GeneratedValue
    @Column(name = "support_msg_id")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "sponsor_id")
    private Long sponsorId; //후원한 팬 pk

    private String message;

    /**
     * 연관관계 편의 메서드
     * @param artist: 후원 받는 아티스트
     * @param sponsor: 후원하는 팬
     * @param message: 후원 메시지
     */
    public void addSupportMsg(Member artist, Member sponsor, String message) {
        this.member = artist;
        this.sponsorId = sponsor.getId();
        this.message = message;
        artist.getSupportMsgs().add(this);
    }
}
