package nuts.muzinut.domain.nuts;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPaymentHistory is a Querydsl query type for PaymentHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentHistory extends EntityPathBase<PaymentHistory> {

    private static final long serialVersionUID = -1240145908L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaymentHistory paymentHistory = new QPaymentHistory("paymentHistory");

    public final NumberPath<Integer> chargeAmount = createNumber("chargeAmount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<NutsStatus> nutsStatus = createEnum("nutsStatus", NutsStatus.class);

    public final DateTimePath<java.time.LocalDateTime> time = createDateTime("time", java.time.LocalDateTime.class);

    public final nuts.muzinut.domain.member.QUser user;

    public QPaymentHistory(String variable) {
        this(PaymentHistory.class, forVariable(variable), INITS);
    }

    public QPaymentHistory(Path<? extends PaymentHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaymentHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaymentHistory(PathMetadata metadata, PathInits inits) {
        this(PaymentHistory.class, metadata, inits);
    }

    public QPaymentHistory(Class<? extends PaymentHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new nuts.muzinut.domain.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

