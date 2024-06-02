package nuts.muzinut.domain.baseEntity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseBoardEntity extends BaseTimeEntity{
    private String title; //music 에서는 음원 이름
    private int view;
}
