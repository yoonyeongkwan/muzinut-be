package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
