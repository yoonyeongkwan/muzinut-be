package nuts.muzinut.repository.board.query;

import com.querydsl.core.QueryResults;
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
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.QUser;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.board.DetailBaseDto;
import nuts.muzinut.dto.board.board.BoardsForm;
import nuts.muzinut.dto.board.board.QBoardsForm;
import nuts.muzinut.dto.board.comment.CommentDto;
import nuts.muzinut.dto.board.comment.ReplyDto;
import nuts.muzinut.exception.board.BoardSearchTypeNotExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nuts.muzinut.domain.board.BoardType.*;
import static nuts.muzinut.domain.board.QAdminBoard.*;
import static nuts.muzinut.domain.board.QAdminUploadFile.adminUploadFile;
import static nuts.muzinut.domain.board.QBoard.*;
import static nuts.muzinut.domain.board.QBookmark.bookmark;
import static nuts.muzinut.domain.board.QComment.*;
import static nuts.muzinut.domain.board.QEventBoard.*;
import static nuts.muzinut.domain.board.QFreeBoard.*;
import static nuts.muzinut.domain.board.QLike.*;
import static nuts.muzinut.domain.board.QRecruitBoard.*;
import static nuts.muzinut.domain.board.QReply.*;
import static nuts.muzinut.domain.member.QUser.*;
import static org.springframework.util.StringUtils.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {

    private final JPAQueryFactory queryFactory;

    //게시판 검색기능
    public Page<BoardsForm> search(BoardType boardType, String searchCond, Pageable pageable) {

        JPAQuery<BoardsForm> selectQuery = queryFactory
                .select(new QBoardsForm(board.id, board.title, board.user.nickname, board.view, board.likeCount, board.createdDt))
                .from(board);

        selectQuery = innerJoinBoardType(selectQuery, boardType);

        if (selectQuery == null) {
            throw new BoardSearchTypeNotExistException("검색하려는 게시판 타입을 입력해주세요");
        }

        QueryResults<BoardsForm> result = selectQuery
                .where(titleInclude(searchCond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdDt.desc())
                .fetchResults();

        List<BoardsForm> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);
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
        } else if (boardType == RECRUIT) {
            return selectQuery.rightJoin(recruitBoard).on(board.id.eq(recruitBoard.id));
        }

        return null;
    }

    public Optional<Board> findById(Long boardId) {
        Board boardResult = queryFactory
                .selectFrom(board)
                .where(board.id.eq(boardId))
                .fetchOne();

        return Optional.ofNullable(boardResult);
    }
}
