package nuts.muzinut.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdminUploadFile is a Querydsl query type for AdminUploadFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminUploadFile extends EntityPathBase<AdminUploadFile> {

    private static final long serialVersionUID = -1165460742L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdminUploadFile adminUploadFile = new QAdminUploadFile("adminUploadFile");

    public final QAdminBoard adminBoard;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originFilename = createString("originFilename");

    public final StringPath storeFilename = createString("storeFilename");

    public QAdminUploadFile(String variable) {
        this(AdminUploadFile.class, forVariable(variable), INITS);
    }

    public QAdminUploadFile(Path<? extends AdminUploadFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdminUploadFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdminUploadFile(PathMetadata metadata, PathInits inits) {
        this(AdminUploadFile.class, metadata, inits);
    }

    public QAdminUploadFile(Class<? extends AdminUploadFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.adminBoard = inits.isInitialized("adminBoard") ? new QAdminBoard(forProperty("adminBoard"), inits.get("adminBoard")) : null;
    }

}

