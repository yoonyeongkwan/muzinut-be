package nuts.muzinut.domain.chat;

import com.sun.mail.imap.protocol.UIDSet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chat_request")
public class ChatRequest {

    @Id @GeneratedValue
    @Column(name = "chat_request")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_user")
    private User requestUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_user")
    private User receiveUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendTime = LocalDateTime.now();

    private String message;
    private boolean isReceived = false;

    public void addChatRequest(User requestUser, User receiveUser, String message) {
        this.requestUser = requestUser;
        this.receiveUser = receiveUser;
        this.message = message;
    }

    public void acceptRequest() {
        this.isReceived = true;
    }
}
