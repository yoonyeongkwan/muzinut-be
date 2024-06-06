package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.ChatMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {
}
