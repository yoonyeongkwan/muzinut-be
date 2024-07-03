package nuts.muzinut.repository.mainpage;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.mainsearch.SearchArtistDto;
import nuts.muzinut.dto.mainsearch.SearchSongDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static nuts.muzinut.domain.member.QFollow.follow;
import static nuts.muzinut.domain.member.QUser.user;
import static nuts.muzinut.domain.music.QAlbum.album;
import static nuts.muzinut.domain.music.QSong.song;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
@Repository
public class MainSearchRepository {

    private final JPAQueryFactory queryFactory;



    public Page<SearchArtistDto> artistSearch(String searchWord, Pageable pageable){
        QueryResults<SearchArtistDto> results = queryFactory
                .select(Projections.constructor(SearchArtistDto.class,
                        user.id,
                        user.profileImgFilename.as("profileImg"),
                        user.nickname,
                        follow.followingMemberId.count().as("followCount")
                ))
                .from(user)
                .leftJoin(follow)
                .on(user.id.eq(follow.followingMemberId))
                .where(user.nickname.contains(searchWord))
                .groupBy(user.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<SearchArtistDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
    public Page<SearchSongDto> songSearch(String searchWord, Pageable pageable){
        QueryResults<SearchSongDto> results = queryFactory
                .select(Projections.constructor(SearchSongDto.class,
                        song.id,
                        album.albumImg,
                        song.title,
                        user.nickname
                ))
                .from(song)
                .join(song.album, album)
                .join(song.user, user)
                .where(song.title.contains(searchWord))
                .groupBy(song.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<SearchSongDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

}
