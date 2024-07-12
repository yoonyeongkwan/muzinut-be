package nuts.muzinut.service.security;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.security.TokenDto;
import nuts.muzinut.exception.token.TokenException;
import nuts.muzinut.jwt.TokenProvider;
import nuts.muzinut.service.member.RedisUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RedisUtil redisUtil;
    private final TokenProvider tokenProvider;

    //리프레시 토큰을 검증하고, 재발급 시켜주는 메서드
    public TokenDto reissueToken(String refreshToken){
        // Refresh Token 검증
        tokenProvider.validateToken(refreshToken);

        // Access Token 에서 User num을 가져옴
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);

        // Redis에서 저장된 Refresh Token 값을 가져옴
        String redisRefreshToken = redisUtil.getData(authentication.getName());
        if(!redisRefreshToken.equals(refreshToken)) {
            throw new TokenException("만료 되거나 유효하지 않은 리프레시 토큰입니다.");
        }
        // 토큰 재발행
        TokenDto tokenDto = new TokenDto(
                tokenProvider.createToken(authentication),
                tokenProvider.createRefreshToken(authentication)
        );

        return tokenDto;
    }
}
