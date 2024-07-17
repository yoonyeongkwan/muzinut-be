package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.ChatMember;
import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {

    @Query("select c from ChatMember c where c.user = :u1 or c.user = :u2")
    List<ChatMember> getChatMembersByUsers(@Param("u1") User u1, @Param("u2") User u2);
}
