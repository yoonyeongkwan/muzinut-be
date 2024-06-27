package nuts.muzinut.domain.baseEntity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseBoardEntity is a Querydsl query type for BaseBoardEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseBoardEntity extends EntityPathBase<BaseBoardEntity> {

    private static final long serialVersionUID = 1953521224L;

    public static final QBaseBoardEntity baseBoardEntity = new QBaseBoardEntity("baseBoardEntity");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDt = _super.modifiedDt;

    public final StringPath title = createString("title");

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QBaseBoardEntity(String variable) {
        super(BaseBoardEntity.class, forVariable(variable));
    }

    public QBaseBoardEntity(Path<? extends BaseBoardEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseBoardEntity(PathMetadata metadata) {
        super(BaseBoardEntity.class, metadata);
    }

}

