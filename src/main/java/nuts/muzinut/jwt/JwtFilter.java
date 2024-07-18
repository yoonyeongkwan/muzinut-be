package nuts.muzinut.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.exception.token.ExpiredTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static nuts.muzinut.jwt.TokenType.*;
import static nuts.muzinut.jwt.TokenType.ILLEGAL_TOKEN;

//public class JwtFilter extends GenericFilterBean

@Slf4j
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private TokenProvider tokenProvider;
    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    //실제 필터링 로직
    //토큰의 인증정보를 SecurityContext 에 저장하는 역할 수행
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        try {
            if (StringUtils.hasText(jwt) && StringUtils.hasText(tokenProvider.validateToken(jwt))) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Security Context 에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
            } else {
                log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
            }
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            setErrorResponse(HttpStatus.BAD_REQUEST, (HttpServletResponse) servletResponse, WRONG_TOKEN); //틀린 토큰
        } catch (ExpiredJwtException e) {
            setErrorResponse(HttpStatus.NOT_ACCEPTABLE, (HttpServletResponse) servletResponse, EXPIRED_TOKEN); //토큰 만료 401
        } catch (UnsupportedJwtException e) {
            setErrorResponse(HttpStatus.BAD_REQUEST, (HttpServletResponse) servletResponse, UNSUPPORTED_TOKEN); //지원되지 않는 JWT 토큰
        } catch (IllegalArgumentException e) {
            setErrorResponse(HttpStatus.BAD_REQUEST, (HttpServletResponse) servletResponse, ILLEGAL_TOKEN); //JWT 토큰이 잘못됨
        }
    }

    //Request Header 에서 토큰 정보를 꺼내오기 위한 메서드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); //bearer 제외한 실제 토큰 값만 반환
        }

        return null;
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse res, String message) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        MessageDto messageDto = new MessageDto(message);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(messageDto);

        res.getWriter().write(jsonResponse);
    }
}
