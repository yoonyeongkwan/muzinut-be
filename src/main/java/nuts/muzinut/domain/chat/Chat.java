package nuts.muzinut.domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Chat {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RoomType roomType; //dm 인지, 단체 톡방인지.. 등

    @Column(name = "participate_count")
    private int participateCount; //채팅방에 참여한 총 인원 수

    public Chat(RoomType roomType) {
        this.roomType = roomType;
    }

    private LocalDateTime created_dt;

    //연관 관계
    @OneToMany(mappedBy = "chat")
    List<ChatMember> chatMembers = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL) //채팅방이 없어지면 해당 채팅방의 메시지들 모두 삭제
    List<Message> messages = new ArrayList<>();

    //비지니스 로직
    public void addParticipate() {
        this.participateCount += 1;
    }

}
