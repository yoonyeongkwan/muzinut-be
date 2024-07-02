package nuts.muzinut.repository.mainpage;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.QFollow;
import nuts.muzinut.domain.music.QSong;
import nuts.muzinut.dto.mainpage.*;
import nuts.muzinut.dto.music.SongPageDto;
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

    public void findMainPageSongData(){


    }

    public List<Tuple> findHotBoard(){
        return queryFactory
                .select(board, freeBoard, recruitBoard, user)
                .from(board)
                .join(board.user, user)
                .leftJoin(freeBoard).on(board.id.eq(freeBoard.id))
                .leftJoin(recruitBoard).on(board.id.eq(recruitBoard.id))
                .orderBy(board.view.desc())
                .limit(5)
                .fetch();
    }

    public List<Tuple> findNewBoard(){
        return queryFactory
                .select(board, freeBoard, recruitBoard, user)
                .from(board)
                .join(board.user, user)
                .leftJoin(freeBoard).on(board.id.eq(freeBoard.id))
                .leftJoin(recruitBoard).on(board.id.eq(recruitBoard.id))
                .orderBy(board.createdDt.desc())
                .limit(8)
                .fetch();
    }

}
