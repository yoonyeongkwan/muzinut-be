package nuts.muzinut.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdminBoard is a Querydsl query type for AdminBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminBoard extends EntityPathBase<AdminBoard> {

    private static final long serialVersionUID = 928402665L;

    public static final QAdminBoard adminBoard = new QAdminBoard("adminBoard");

    public final nuts.muzinut.domain.baseEntity.QBaseBoardEntity _super = new nuts.muzinut.domain.baseEntity.QBaseBoardEntity(this);

    public final NumberPath<Long> adminId = createNumber("adminId", Long.class);

    public final ListPath<AdminUploadFile, QAdminUploadFile> adminUploadFiles = this.<AdminUploadFile, QAdminUploadFile>createList("adminUploadFiles", AdminUploadFile.class, QAdminUploadFile.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDt = _super.modifiedDt;

    //inherited
    public final StringPath title = _super.title;

    //inherited
    public final NumberPath<Integer> view = _super.view;

    public QAdminBoard(String variable) {
        super(AdminBoard.class, forVariable(variable));
    }

    public QAdminBoard(Path<? extends AdminBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminBoard(PathMetadata metadata) {
        super(AdminBoard.class, metadata);
    }

}

