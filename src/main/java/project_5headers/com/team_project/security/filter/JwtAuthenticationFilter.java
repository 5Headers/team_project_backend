package project_5headers.com.team_project.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project_5headers.com.team_project.repository.UserRepository;
import project_5headers.com.team_project.security.jwt.JwtUtils;
import project_5headers.com.team_project.security.model.PrincipalUser;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements Filter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();

        // ===== 디버깅 로그 =====
        System.out.println("🔎 JwtAuthenticationFilter: 요청 path = " + path);

        // 회원가입/로그인/지도 API/OPTIONS 요청은 인증 제외
        if (path.startsWith("/auth/signup") ||
                path.startsWith("/auth/signin") ||
                path.startsWith("/api/maps") ||   // ✅ 지도 API는 토큰 검사 제외
                path.startsWith("/oauth2/") ||    // ✅ OAuth2 로그인도 토큰 검사 제외
                path.startsWith("/error") ||
                "OPTIONS".equalsIgnoreCase(req.getMethod())) {

            System.out.println("✅ JwtAuthenticationFilter: 인증 제외 처리됨 → " + path);
            chain.doFilter(request, response);
            return;
        }

        // ===== Authorization 헤더 검사 =====
        String authHeader = req.getHeader("Authorization");
        if (jwtUtils.isBearer(authHeader)) {
            String token = jwtUtils.removeBearer(authHeader);
            try {
                if (jwtUtils.validateToken(token)) {
                    Claims claims = jwtUtils.getClaims(token);

                    // 토큰에서 ID 가져오기
                    String userIdStr = claims.getId();
                    Integer userId = Integer.valueOf(userIdStr);

                    userRepository.getUserByUserId(userId).ifPresentOrElse(user -> {
                        PrincipalUser principal = PrincipalUser.builder()
                                .userId(user.getUserId())
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .email(user.getEmail())
                                .userRoles(user.getUserRoles() != null ? user.getUserRoles() : List.of())
                                .build();

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        System.out.println("✅ JwtAuthenticationFilter: 인증 성공 userId=" + userId);

                    }, () -> {
                        throw new AuthenticationServiceException("사용자를 찾을 수 없습니다.");
                    });
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("⚠️ JwtAuthenticationFilter: Authorization 헤더 없음/형식 오류");
        }

        chain.doFilter(request, response);
    }
}
