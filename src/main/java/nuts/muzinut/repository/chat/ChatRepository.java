package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select c from Chat c join fetch c.chatMembers where c.id = :id")
    Optional<Chat> findChatWithMembers(@Param("id") Long id);
}
