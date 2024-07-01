package nuts.muzinut.repository.board.query;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QComment.comment;
import static nuts.muzinut.domain.board.QFreeBoard.freeBoard;
import static nuts.muzinut.domain.board.QLike.like;
import static nuts.muzinut.domain.board.QRecruitBoard.recruitBoard;
import static nuts.muzinut.domain.board.QReply.reply;
import static nuts.muzinut.domain.member.QUser.user;

@Slf4j
@RequiredArgsConstructor
@Repository
public class RecruitBoardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Tuple> getDetailRecruitBoard(Long boardId) {

        return queryFactory
                .select(board, recruitBoard,
                        JPAExpressions
                                .select(like.count())
                                .from(like)
                                .where(like.board.id.eq(boardId)))
                .from(board)
                .leftJoin(recruitBoard).on(board.id.eq(recruitBoard.id))
                .leftJoin(board.user, user).fetchJoin() //추가
                .leftJoin(board.comments, comment).fetchJoin()
                .leftJoin(comment.replies, reply)
                .where(board.id.eq(boardId))
                .fetch();
    }
}
