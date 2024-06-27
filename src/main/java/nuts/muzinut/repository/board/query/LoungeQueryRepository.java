package nuts.muzinut.repository.board.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.QLounge;
import nuts.muzinut.dto.board.comment.CommentDto;
import nuts.muzinut.dto.board.comment.ReplyDto;
import org.springframework.stereotype.Repository;

import java.util.List;

import static nuts.muzinut.domain.board.QAdminBoard.adminBoard;
import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QComment.comment;
import static nuts.muzinut.domain.board.QFreeBoard.freeBoard;
import static nuts.muzinut.domain.board.QLike.like;
import static nuts.muzinut.domain.board.QLounge.*;
import static nuts.muzinut.domain.board.QReply.reply;
import static nuts.muzinut.domain.member.QUser.user;

@Slf4j
@RequiredArgsConstructor
@Repository
public class LoungeQueryRepository {

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

    public List<Tuple> getDetailLoungeTest(Long boardId) {

        return queryFactory
                .select(board, freeBoard)
                .from(board)
                .leftJoin(freeBoard).on(board.id.eq(freeBoard.id))
                .leftJoin(board.comments, comment)
                .leftJoin(comment.replies, reply)
                .leftJoin(reply.user, user)
                .where(board.id.eq(boardId))
                .fetch();
    }
}
