package oneus.GSMATCH.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import oneus.GSMATCH.global.util.UserRoleEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    /* 1. JWT 데이터 준비하기 */
    public static final String AUTHORIZATION_HEADER = "Authorization";      // Header KEY 값
    public static final String AUTHORIZATION_KEY = "auth";      // 사용자 권한 값의 KEY
    public static final String BEARER_PREFIX = "Bearer ";       // Token 식별자
    private static final long TOKEN_TIME = 60 * 60 * 1000L;        // 토큰 만료시간 : 60분
    private static final long REFRESH_TOKEN_TIME = 24 * 60 * 60 * 1000L;        // 토큰 만료시간 : 60분

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey (application.yml 에 추가해둔 값)
    private String secretKey;       // 그 값을 가져와서 secretKey 변수에 넣는다
    private static Key key;        // Secret key 를 담을 변수
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;     // 사용할 알고리즘 선택

    @PostConstruct      // 한 번만 받으면 값을 사용할 때마다, 매번 요청을 새로 호출하는 것을 방지
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /* 2. JWT 토큰 생성 */
    // 인증된 토큰을 기반으로 JWT 토큰을 발급
    public static String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        // 암호화
        return Jwts.builder()
                        .setSubject(username)               // 사용자 식별자값(ID). 여기에선 username 을 넣음
                        .claim(AUTHORIZATION_KEY, role)     // 사용자 권한 (key, value)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))   // 만료 시간 : 현재시간 date.getTime() + 위에서 지정한 토큰 만료시간(60분)
                        .setIssuedAt(date)                  // 발급일
                        .signWith(key, signatureAlgorithm)  // 암호화 알고리즘 (Secret key, 사용할 알고리즘 종류)
                        .compact();
    }

    public static String createRefreshToken(String username) {
        Date date = new Date();

        return Jwts.builder()
                        .setIssuedAt(date)
                        .setSubject(username)
                        .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))   // 만료 시간 : 현재시간 date.getTime() + 위에서 지정한 토큰 만료시간(60분)
                        .signWith(key, signatureAlgorithm)  // 암호화 알고리즘 (Secret key, 사용할 알고리즘 종류)
                        .compact();
    }

    // 3. header 에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /* 4. JWT 토큰 검증 */
    // 토큰의 만료, 위/변조 를 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        } catch (Exception e) {
            log.error("=========== 토큰 검증 중 예외가 발생 했습니다. ===========");
        }
        return false;
    }

    /* 5. JWT 토큰에서 사용자 정보 가져오기 */
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}