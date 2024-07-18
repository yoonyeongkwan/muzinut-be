package nuts.muzinut.repository.board.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.QUser;
import nuts.muzinut.dto.board.board.BoardsForm;
import nuts.muzinut.dto.board.board.QBoardsForm;
import nuts.muzinut.dto.board.comment.CommentDto;
import nuts.muzinut.dto.board.comment.ReplyDto;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nuts.muzinut.domain.board.BoardType.*;
import static nuts.muzinut.domain.board.QAdminBoard.*;
import static nuts.muzinut.domain.board.QBoard.*;
import static nuts.muzinut.domain.board.QComment.*;
import static nuts.muzinut.domain.board.QEventBoard.*;
import static nuts.muzinut.domain.board.QFreeBoard.*;
import static nuts.muzinut.domain.board.QLike.*;
import static nuts.muzinut.domain.board.QReply.*;
import static nuts.muzinut.domain.member.QUser.*;
import static org.springframework.util.StringUtils.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<BoardsForm> search(BoardType boardType, String searchCond) {

        JPAQuery<BoardsForm> selectQuery = queryFactory
                .select(new QBoardsForm(board.id, board.title, board.user.nickname, board.view, board.likeCount, board.createdDt))
                .from(board);

        selectQuery = innerJoinBoardType(selectQuery, boardType);

        if (selectQuery == null) {
            return new ArrayList<>();
        }

        return selectQuery
                .where(titleInclude(searchCond))
                .fetch();
    }

    private BooleanExpression titleInclude(String title) {
        return board.title.contains(title);
    }

    private <T> JPAQuery<T> innerJoinBoardType(JPAQuery<T> selectQuery, BoardType boardType) {
        if (boardType == ADMIN) {
            return selectQuery.rightJoin(adminBoard).on(board.id.eq(adminBoard.id));
        } else if (boardType == EVENT) {
            return selectQuery.rightJoin(eventBoard).on(board.id.eq(eventBoard.id));
        } else if (boardType == FREE) {
            return selectQuery.rightJoin(freeBoard).on(board.id.eq(freeBoard.id));
        }

        return null;
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
                                .where(like.board.id.eq(boardId)))
                .from(board)
                .leftJoin(board.comments, comment).fetchJoin()
                .leftJoin(comment.replies, reply)
                .leftJoin(reply.user, user)
                .where(board.id.eq(boardId))
                .fetch();
    }

    public Optional<Board> findById(Long boardId) {
        Board boardResult = queryFactory
                .selectFrom(board)
                .where(board.id.eq(boardId))
                .fetchOne();

        return Optional.ofNullable(boardResult);
    }

}
