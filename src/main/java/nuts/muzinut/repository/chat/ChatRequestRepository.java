package nuts.muzinut.repository.chat;

import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.chat.ChatRequest;
import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRequestRepository extends JpaRepository<ChatRequest, Long> {

    boolean existsByRequestUserAndReceiveUser(User requestUser, User receiveUser);

    Optional<ChatRequest> findByRequestUserAndReceiveUser(User requestUser, User receiveUser);

    @Query("select c from ChatRequest c join fetch c.requestUser order by c.sendTime desc")
    List<ChatRequest> findByReceiveUser(User receiveUser);
}
