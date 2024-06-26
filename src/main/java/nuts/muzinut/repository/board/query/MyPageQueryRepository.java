package nuts.muzinut.repository.board.query;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QComment.comment;
import static nuts.muzinut.domain.board.QLike.like;
import static nuts.muzinut.domain.board.QLounge.lounge;
import static nuts.muzinut.domain.board.QReply.reply;
import static nuts.muzinut.domain.member.QUser.user;

@RequiredArgsConstructor
@Repository
public class MyPageQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Tuple> getDetailLounge(Long boardId) {

        return queryFactory
                .select(board, lounge,
                        JPAExpressions
                                .select(like.count())
                                .from(like)
                                .where(like.board.id.eq(boardId)))
                .from(board)
                .leftJoin(lounge).on(board.id.eq(lounge.id))
                .leftJoin(board.user, user).fetchJoin() //추가
                .leftJoin(board.comments, comment).fetchJoin()
                .leftJoin(comment.replies, reply)
                .where(board.id.eq(boardId))
                .fetch();
    }
}
