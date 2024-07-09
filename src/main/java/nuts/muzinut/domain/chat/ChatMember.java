package nuts.muzinut.domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nuts.muzinut.domain.member.User;

@Entity
@Getter @Setter
@Table(name = "chat_member")
public class ChatMember {

    @Id @GeneratedValue
    @Column(name = "chat_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    //채팅창에 참가
    public ChatMember (User user, Chat chat) {
        this.user = user;
        this.chat = chat;
        chat.getChatMembers().add(this);
        user.getChatMembers().add(this);
    }
}
