package nuts.muzinut.repository.board.query;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.QBoard;
import org.springframework.stereotype.Repository;

import java.util.List;

import static nuts.muzinut.domain.board.QBoard.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class FreeBoardQueryRepository {

    private final JPAQueryFactory queryFactory;

//    public List<Tuple> getDetailFreeBoard(Long boardId) {
//
//        return queryFactory
//                .select(board)
//                .from(board)
//
//    }
}
