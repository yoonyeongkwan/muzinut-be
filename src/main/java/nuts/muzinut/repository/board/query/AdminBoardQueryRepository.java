package nuts.muzinut.repository.board.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.QAdminBoard;
import nuts.muzinut.domain.board.QAdminUploadFile;
import nuts.muzinut.domain.board.QBookmark;
import nuts.muzinut.domain.board.QLike;
import nuts.muzinut.domain.member.QUser;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.board.DetailBaseDto;
import nuts.muzinut.dto.board.comment.CommentDto;
import nuts.muzinut.dto.board.comment.ReplyDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

import static nuts.muzinut.domain.board.QAdminBoard.*;
import static nuts.muzinut.domain.board.QAdminUploadFile.*;
import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QBookmark.*;
import static nuts.muzinut.domain.board.QComment.comment;
import static nuts.muzinut.domain.board.QFreeBoard.freeBoard;
import static nuts.muzinut.domain.board.QLike.like;
import static nuts.muzinut.domain.board.QReply.reply;
import static nuts.muzinut.domain.member.QUser.*;
import static nuts.muzinut.domain.member.QUser.user;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AdminBoardQueryRepository {

    private final JPAQueryFactory queryFactory;

/*    public List<Tuple> getDetailAdminBoard(Long boardId) {

        return queryFactory
                .select(board, adminBoard,
                        JPAExpressions
                                .select(like.count())
                                .from(like)
                                .where(like.board.id.eq(boardId)))
                .from(board)
                .leftJoin(adminBoard).on(board.id.eq(adminBoard.id))
                .leftJoin(adminBoard.adminUploadFiles, adminUploadFile)
                .leftJoin(board.user, user).fetchJoin() //추가
                .leftJoin(board.comments, comment).fetchJoin()
                .leftJoin(comment.replies, reply) //주석해제시 정상 작동
                .where(board.id.eq(boardId))
                .fetch();
    }*/

    public List<Tuple> getDetailAdminBoard(Long boardId, User user) {

        CaseBuilder caseBuilder = new CaseBuilder();

        BooleanExpression isLikeExpression = isLikeUserNotNullAndEquals(user, boardId);
        BooleanExpression isBookmarkExpression = isBookmarkUserNotNullAndEquals(user, boardId);

        return queryFactory
                .select(board, adminBoard,
                        JPAExpressions
                                .select(like.count())
                                .from(like)
                                .where(like.board.id.eq(boardId)),
                        Projections.fields(DetailBaseDto.class,
                                isLikeExpression.as("boardLikeStatus"),
                                isBookmarkExpression.as("isBookmark")))
                .from(board)
                .leftJoin(adminBoard).on(board.id.eq(adminBoard.id))
                .leftJoin(adminBoard.adminUploadFiles, adminUploadFile)
                .leftJoin(board.user, QUser.user).fetchJoin() //추가
                .leftJoin(board.comments, comment).fetchJoin()
                .where(board.id.eq(boardId))
                .fetch();
    }

    private BooleanExpression isLikeUserNotNullAndEquals(User user, Long boardId) {
        if (user == null) {
            return Expressions.FALSE;
        } else {
            return JPAExpressions
                    .selectOne()
                    .from(like)
                    .where(like.board.id.eq(boardId)
                            .and(like.user.eq(user)))
                    .exists();
        }
    }

    private BooleanExpression isBookmarkUserNotNullAndEquals(User user, Long boardId) {
        if (user == null) {
            return Expressions.FALSE;
        } else {
            return JPAExpressions
                    .selectOne()
                    .from(bookmark)
                    .where(bookmark.board.id.eq(boardId)
                            .and(bookmark.user.eq(user)))
                    .exists();
        }
    }

    public List<Tuple> getDetailBoard(Long boardId) {

        return queryFactory
                .select(board,
                        Projections.fields(CommentDto.class, comment.id, comment.content,
                                comment.user.nickname.as("commentWriter"), comment.createdDt),
                        Projections.fields(ReplyDto.class, reply.id, reply.content, reply.comment.id.as("commentId"),
                                reply.user.nickname.as("replyWriter"), reply.createdDt),
                        JPAExpressions
                                .select(like.count())
                                .from(like)
                                .where(like.board.id.eq(boardId)), adminBoard)
                .from(board)
//                .leftJoin(adminBoard)
//                .on(board.id.eq(adminBoard.id))
                .leftJoin(board.comments, comment)
                .leftJoin(comment.replies, reply)
                .leftJoin(reply.user, user)
                .where(board.id.eq(boardId))
                .fetch();
    }
}
