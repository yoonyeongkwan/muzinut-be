package nuts.muzinut.repository.mainpage;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.mainpage.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QFreeBoard.freeBoard;
import static nuts.muzinut.domain.board.QRecruitBoard.recruitBoard;
import static nuts.muzinut.domain.member.QUser.*;
import static nuts.muzinut.domain.music.QAlbum.*;
import static nuts.muzinut.domain.music.QSong.*;
import static nuts.muzinut.domain.music.QPlayView.*;
import static nuts.muzinut.domain.member.QFollow.*;

@RequiredArgsConstructor
@Repository
public class MainPageRepository {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    EntityManager em;

    public List<HotSongDto> findTOP10Song() {
        return queryFactory
                .select(Projections.constructor(HotSongDto.class,
                        song.id,
                        album.albumImg,
                        song.title,
                        user.nickname))
                .from(playView)
                .join(playView.song, song)
                .join(song.user, user)
                .join(song.album, album)
                .groupBy(song.id)
                .orderBy(playView.id.count().desc())
                .limit(10)
                .fetch();
    }

    public List<HotArtistDto> findTOP5Artist() {
        return queryFactory
                .select(Projections.constructor(HotArtistDto.class,
                        user.id,
                        user.profileImgFilename,
                        user.nickname))
                .from(user)
                .leftJoin(follow)
                .on(user.id.eq(follow.followingMemberId))
                .groupBy(user.id)
                .orderBy(follow.followingMemberId.count().desc())
                .limit(5)
                .fetch();
    }

    public List<NewSongDto> findNewSong() {
        return queryFactory
                .select(Projections.constructor(NewSongDto.class,
                        song.id,
                        album.albumImg,
                        song.title,
                        user.nickname))
                .from(song)
                .join(song.album, album)
                .join(song.user, user)
                .where(song.id.in(
                        JPAExpressions.select(song.id)
                                .from(song)
                                .orderBy(song.createdDt.desc())
                ))
                .limit(15)
                .fetch();
    }


    public List<Object[]> findHotBoard(){
         String sql = "SELECT " +
                "b.board_id, " +
                "b.title, " +
                "u.nickname, " +
                "b.view, " +
                "b.dtype " +
                "FROM board b " +
                "JOIN users u ON b.user_id = u.user_id " +
                 "WHERE b.dtype = 'FreeBoard' OR b.dtype = 'RecruitBoard'" +
                "ORDER BY b.view DESC " +
                "LIMIT 5 ";
        Query nativeQuery = em.createNativeQuery(sql);
        return nativeQuery.getResultList();
    }

    public List<Object[]> findNewBoard(){
        String sql = "(SELECT " +
                    "b.board_id, " +
                    "b.title, " +
                    "u.nickname, " +
                    "b.dtype " +
                    "FROM board b " +
                    "JOIN users u ON b.user_id = u.user_id " +
                    "WHERE b.dtype = 'FreeBoard' " +
                    "ORDER BY b.created_dt DESC " +
                    "LIMIT 4) " +
                    "UNION All " +
                    "(SELECT " +
                    "b.board_id, " +
                    "b.title, " +
                    "u.nickname, " +
                    "b.dtype " +
                    "FROM board b " +
                    "JOIN users u ON b.user_id = u.user_id " +
                    "WHERE b.dtype = 'RecruitBoard' " +
                    "ORDER BY b.created_dt DESC " +
                    "LIMIT 4)";
        Query nativeQuery = em.createNativeQuery(sql);
        return nativeQuery.getResultList();
    }

}
