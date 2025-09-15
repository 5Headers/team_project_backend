package project_5headers.com.team_project.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final Key KEY;

    public JwtUtils(@Value("${jwt.secret}") String secret) {
        this.KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    // AccessToken 생성 (id만 사용)
    public String generateAccessToken(String userId) {
        return Jwts.builder()
                .setSubject("AccessToken")
                .setId(userId) // User 객체 없이 id만 사용
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60L * 60L * 24L * 30L)) // 30일
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // VerifyToken 생성 (id만 사용)
    public String generateVerifyToken(String userId) {
        return Jwts.builder()
                .setSubject("VerifyToken")
                .setId(userId)
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60L * 3L)) // 3분
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Bearer 체크
    public boolean isBearer(String token) {
        return token != null && token.startsWith("Bearer ");
    }

    // Bearer 제거
    public String removeBearer(String token) {
        return token.replaceFirst("Bearer ", "");
    }

    // Claims 파싱
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}