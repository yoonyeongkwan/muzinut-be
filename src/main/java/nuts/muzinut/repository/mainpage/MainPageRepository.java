package nuts.muzinut.repository.mainpage;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.member.QFollow;
import nuts.muzinut.domain.music.QSong;
import nuts.muzinut.dto.mainpage.HotArtistDto;
import nuts.muzinut.dto.mainpage.HotSongDto;
import org.springframework.stereotype.Repository;

import java.util.List;

import static nuts.muzinut.domain.member.QUser.*;
import static nuts.muzinut.domain.music.QAlbum.*;
import static nuts.muzinut.domain.music.QSong.*;
import static nuts.muzinut.domain.music.QPlayView.*;
import static nuts.muzinut.domain.member.QFollow.*;

@RequiredArgsConstructor
@Repository
public class MainPageRepository {

    private final JPAQueryFactory queryFactory;

    public List<HotSongDto> findTOP10Song() {
        return queryFactory
                .select(Projections.constructor(HotSongDto.class,
                        song.title,
                        user.nickname,
                        song.id,
                        album.albumImg))
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
                .orderBy(follow.id.count().desc())
                .limit(5)
                .fetch();
    }



}
