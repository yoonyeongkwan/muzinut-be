package nuts.muzinut.repository.mypick.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.mypick.FindArtistDto;
import nuts.muzinut.dto.mypick.MyPickCommentDto;
import nuts.muzinut.dto.mypick.MyPickRankingDto;
import org.springframework.stereotype.Repository;

import java.util.List;

import static nuts.muzinut.domain.member.QUser.user;
import static nuts.muzinut.domain.mypick.QMypickComment.mypickComment;

@RequiredArgsConstructor
@Repository
public class MyPickQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<FindArtistDto> findArtist(String nickname) {
        return queryFactory
                .select(Projections.constructor(FindArtistDto.class,
                        user.id,
                        user.nickname,
                        user.receiveVote,
                        user.profileImgFilename
                ))
                .from(user)
                .where(user.nickname.contains(nickname))
                .fetch();
    }

    public List<MyPickRankingDto> findMyPickRanking() {
        return queryFactory
                .select(Projections.constructor(MyPickRankingDto.class,
                        user.id,
                        user.nickname,
                        user.receiveVote,
                        user.profileImgFilename
                ))
                .from(user)
                .where()
                .orderBy(user.receiveVote.desc())
                .groupBy(user.id)
                .limit(3)
                .fetch();
    }

    public List<MyPickCommentDto> getComment() {
        return queryFactory
                .select(Projections.constructor(MyPickCommentDto.class,
                        mypickComment.id,
                        user.nickname,
                        mypickComment.comment,
                        user.profileImgFilename
                ))
                .from(mypickComment)
                .join(mypickComment.user, user)
                .orderBy(mypickComment.createdDt.desc())
                .fetch();
    }
}
