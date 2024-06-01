package nuts.muzinut.repository.member;

import nuts.muzinut.domain.member.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailboxRepository extends JpaRepository<Mailbox, Long> {
}
