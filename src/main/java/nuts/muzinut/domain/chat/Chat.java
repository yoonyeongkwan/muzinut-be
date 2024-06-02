package nuts.muzinut.domain.chat;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Chat {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RoomType roomType; //dm 인지, 단체 톡방인지.. 등

    private LocalDateTime created_dt;
}
