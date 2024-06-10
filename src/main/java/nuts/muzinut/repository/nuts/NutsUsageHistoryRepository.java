package nuts.muzinut.repository.nuts;

import nuts.muzinut.domain.nuts.NutsUsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutsUsageHistoryRepository extends JpaRepository<NutsUsageHistory, Long> {
}
