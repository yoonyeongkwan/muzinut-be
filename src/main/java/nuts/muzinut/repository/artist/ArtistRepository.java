package nuts.muzinut.repository.artist;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.artist.HotArtistDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static nuts.muzinut.domain.member.QFollow.follow;
import static nuts.muzinut.domain.member.QUser.user;

@RequiredArgsConstructor
@Repository
public class ArtistRepository {

    private final JPAQueryFactory queryFactory;

    public Page<HotArtistDto> hotArtist(Pageable pageable) {

        List<HotArtistDto> content = queryFactory
                .select(Projections.fields(HotArtistDto.class,
                        user.id,
                        user.nickname,
                        follow.count().as("follower"),
                        user.profileImgFilename.as("img")))
                .from(follow)
                .join(user).on(user.id.eq(follow.followingMemberId))
                .groupBy(user.id)
                .orderBy(follow.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = 50;

        return new PageImpl<>(content, pageable, total);
    }
}
