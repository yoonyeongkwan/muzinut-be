package nuts.muzinut.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;

    /**
     * @param secret: 토큰 시크릿 키
     * @param tokenValidityInSeconds: 토큰 지속 시간
     */
    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    // 빈이 생성되고 주입을 받은 후에 secret 값을 Base64 Decode 해서 key 변수에 할당
    @Override
    public void afterPropertiesSet() {
        //secret 은 Base64로 인코딩된 문자열임.
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        //HMAC-SHA 알고리즘에 적합한 키 객체를 생성. 이 키는 JWT 토큰의 서명 생성 및 검증에 사용
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * @param authentication: 스프링 시큐리티에서 인증된 사용자의 정보를 가지고 있음
     */
    public String createToken(Authentication authentication) {
        //사용자의 권한을 확인하는데 사용
        String authorities = authentication.getAuthorities().stream() //사용자의 권한 목록을 stream 으로 돌림
                .map(GrantedAuthority::getAuthority) //권한 이름을 문자열로 반환
                //사용자가 "ROLE_USER"와 "ROLE_ADMIN" 권한을 가지고 있다면, 이 과정의 결과는 "ROLE_USER,ROLE_ADMIN"과 같은 문자열
                .collect(Collectors.joining(","));

        //토큰의 expire 시간을 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities) //정보 저장
                .signWith(key, SignatureAlgorithm.HS512) //사용할 암호화 알고리즘과 서명에 들어갈 secret 값 설정
                .setExpiration(validity) //expire 시간
                .compact();
    }

    public Authentication getAuthentication(String token) {
        //클레임은 토큰에서 사용할 정보임. json 형태로 다수의 정보를 넣을 수 있음
        //클레임은 payload 에 해당하는 부분임
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        claims.put("id", "memberId"); //Todo 인증한 회원의 pk 추가

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")) //권한 정보들을 가져와서 , 로 분리함
                        .map(SimpleGrantedAuthority::new)  //각 권한 문자열에 대해 SimpleGrantedAuthority 객체를 생성
                        .collect(Collectors.toList()); //스트림의 요소들을 리스트로 수집

        //claims.getSubject() 는 JWT 토큰의 주체(사용자 이름)를 가져옴. 이 값은 보통 사용자의 ID나 사용자명
        //User 객체는 사용자 인증과 권한 부여를 처리하는 데 사용
        User principal = new User(claims.getSubject(), "", authorities);

        //principal: 사용자 정보 credentials: 인증 자격 증명 (여기서는 JWT 토큰) authorities: 사용자 권한 정보
        return new UsernamePasswordAuthenticationToken(principal, token, authorities); //스프링 시큐리티의 인증 및 권한 부여 과정에서 사용
    }

    public boolean validateToken(String token) {
        try {
            //setSigningKey 는 JWT의 서명을 검증할 때 사용할 키를 설정
            //parseClaimsJws() 메서드는 JWT 토큰을 파싱하고, 서명을 검증하며, 클레임을 추출
            Jws<Claims> jwsClaims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            Claims claims = jwsClaims.getBody();
            String subject = claims.getSubject(); //토큰의 주체 (사용자 이름 등)
            Date expiration = claims.getExpiration(); //토큰 만료 시간

            log.info("token subject: {}", subject);
            log.info("token expiration: {}", expiration);

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
