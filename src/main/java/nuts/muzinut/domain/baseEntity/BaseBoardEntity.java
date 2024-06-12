package nuts.muzinut.domain.baseEntity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public abstract class BaseBoardEntity extends BaseTimeEntity{
    private String title; //music 에서는 음원 이름
    private int view;
}
