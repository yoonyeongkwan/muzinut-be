package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.ReadMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadMessageRepository extends JpaRepository<ReadMessage, Long> {
}
