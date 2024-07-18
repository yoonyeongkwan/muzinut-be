package nuts.muzinut.domain.nuts;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNutsUsageHistory is a Querydsl query type for NutsUsageHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNutsUsageHistory extends EntityPathBase<NutsUsageHistory> {

    private static final long serialVersionUID = -1617870281L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNutsUsageHistory nutsUsageHistory = new QNutsUsageHistory("nutsUsageHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath supportMsg = createString("supportMsg");

    public final StringPath usedContent = createString("usedContent");

    public final DateTimePath<java.time.LocalDateTime> usedDt = createDateTime("usedDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> usedNutsCount = createNumber("usedNutsCount", Integer.class);

    public final nuts.muzinut.domain.member.QUser user;

    public QNutsUsageHistory(String variable) {
        this(NutsUsageHistory.class, forVariable(variable), INITS);
    }

    public QNutsUsageHistory(Path<? extends NutsUsageHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNutsUsageHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNutsUsageHistory(PathMetadata metadata, PathInits inits) {
        this(NutsUsageHistory.class, metadata, inits);
    }

    public QNutsUsageHistory(Class<? extends NutsUsageHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new nuts.muzinut.domain.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

