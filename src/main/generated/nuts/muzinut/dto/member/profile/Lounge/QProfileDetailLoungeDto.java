package nuts.muzinut.dto.member.profile.Lounge;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * nuts.muzinut.dto.member.profile.Lounge.QProfileDetailLoungeDto is a Querydsl Projection type for ProfileDetailLoungeDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProfileDetailLoungeDto extends ConstructorExpression<ProfileDetailLoungeDto> {

    private static final long serialVersionUID = 834064661L;

    public QProfileDetailLoungeDto(com.querydsl.core.types.Expression<Boolean> boardLikeStatus, com.querydsl.core.types.Expression<Boolean> isBookmark, com.querydsl.core.types.Expression<String> profileBannerImgName, com.querydsl.core.types.Expression<String> profileImgName, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> intro, com.querydsl.core.types.Expression<Long> followingCount, com.querydsl.core.types.Expression<Long> followersCount, com.querydsl.core.types.Expression<Boolean> followStatus) {
        super(ProfileDetailLoungeDto.class, new Class<?>[]{boolean.class, boolean.class, String.class, String.class, String.class, String.class, long.class, long.class, boolean.class}, boardLikeStatus, isBookmark, profileBannerImgName, profileImgName, nickname, intro, followingCount, followersCount, followStatus);
    }

}

