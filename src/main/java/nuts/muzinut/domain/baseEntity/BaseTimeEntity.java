package nuts.muzinut.domain.baseEntity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseTimeEntity {
    private LocalDateTime created_dt;
    private LocalDateTime modified_dt;
}


