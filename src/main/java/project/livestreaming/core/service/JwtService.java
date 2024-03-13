package project.livestreaming.core.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.livestreaming.core.entity.Refresh;
import project.livestreaming.core.repository.RefreshRepository;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JwtService {

    private final RefreshRepository refreshRepository;

    private SecretKey secretKey;

    @Value("${spring.jwt.secret}")
    private void setSecret(String secret) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    @Transactional
    public void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Refresh refreshEntity = Refresh.builder()
                .username(username)
                .refresh(refresh)
                .expiration(date.toString())
                .build();

        refreshRepository.save(refreshEntity);
    }

    public String getUsername(String token) {
        return getPayload(token)
                .get("username", String.class);
    }

    public List<String> getRole(String token) {
        return getPayload(token)
                .get("roles", List.class);
    }

    public Boolean isExpired(String token) {
        return getPayload(token)
                .getExpiration()
                .before(new Date());
    }

    public String getCategory(String token) {
        return getPayload(token)
                .get("category", String.class);
    }

    public String createJwt(String category, String username, List<String> roles, Long expiredMs) {

        return Jwts.builder()
                .claim("category", category)
                .claim("username", username)
                .claim("roles", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    private Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    @Transactional
    public void refreshReissue(HttpServletRequest request, HttpServletResponse response) {
        //get refresh token
        String refresh = null;

        // 쿠키에 refresh 라는 key 값이 있는지 확인
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            throw new IllegalArgumentException("refresh token null");
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        if (!getCategory(refresh).equals("refresh")) {
            throw new IllegalArgumentException("invalid refresh token");
        }

        //expired check
        try {
            isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("refresh token expired");
        }

        //DB에 저장되어 있는지 확인
        if (!refreshRepository.existsByRefresh(refresh)) {
            throw new IllegalArgumentException("invalid refresh token");
        }

        String username = getUsername(refresh);
        List<String> roles = getRole(refresh);

        //make new JWT
        String newAccess = createJwt("access", username, roles, 600000L);
        String newRefresh = createJwt("refresh", username, roles, 86400000L);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);

        addRefreshEntity(username, newRefresh, 86400000L);

        //response
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));
    }
}
