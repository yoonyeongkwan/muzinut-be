package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
