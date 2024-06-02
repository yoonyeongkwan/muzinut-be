package nuts.muzinut.domain.chat;

import jakarta.persistence.*;
import nuts.muzinut.domain.member.Member;

@Entity
@Table(name = "read_message")
public class ReadMessage {

    @Id @GeneratedValue
    @Column(name = "read_message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(name = "member_id")
    private Long memberId;
}
