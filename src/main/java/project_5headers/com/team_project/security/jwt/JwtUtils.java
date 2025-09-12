package project_5headers.com.team_project.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project_5headers.com.team_project.entity.User;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final Key KEY;

    // secret은 application.properties에 Base64로 넣어두자
    public JwtUtils(@Value("${jwt.secret}") String secret) {
        this.KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    // AccessToken 생성 (예: 30일 유효)
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject("AccessToken")
                .setId(user.getUserId().toString())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60L * 60L * 24L * 30L))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // VerifyToken 생성 (예: 3분 유효)
    public String generateVerifyToken(User user) {
        return Jwts.builder()
                .setSubject("VerifyToken")
                .setId(user.getUserId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60L * 3L))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Authorization 헤더가 Bearer인지 체크
    public boolean isBearer(String token) {
        return token != null && token.startsWith("Bearer ");
    }

    // Bearer 제거
    public String removeBearer(String token) {
        return token.replaceFirst("Bearer ", "");
    }

    // 토큰 Claims 파싱
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
