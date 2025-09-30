package project_5headers.com.team_project.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();

        System.out.println("🔎 JwtAuthenticationFilter: 요청 path = " + path);

        // 회원가입/로그인/지도 API/OPTIONS 요청은 인증 제외
        if (path.startsWith("/auth/signup") ||
                path.startsWith("/auth/signin") ||
                path.startsWith("/api/maps") ||
                path.startsWith("/oauth2/") ||
                path.startsWith("/error") ||
                path.startsWith("/login") ||
                "OPTIONS".equalsIgnoreCase(req.getMethod())) {

            System.out.println("✅ JwtAuthenticationFilter: 인증 제외 처리됨 → " + path);
            chain.doFilter(request, response);
            return;
        }

        // ===== Authorization 헤더 검사 =====
        String authHeader = req.getHeader("Authorization");

        if (!jwtUtils.isBearer(authHeader)) {
            // 헤더가 없거나 형식 오류 → 401 반환
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing or invalid");
            return;
        }

        String token = jwtUtils.removeBearer(authHeader);

        try {
            if (!jwtUtils.validateToken(token)) {
                // 토큰 유효하지 않음 → 401 반환
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }

            Claims claims = jwtUtils.getClaims(token);
            String userIdStr = claims.getId();
            Integer userId = Integer.valueOf(userIdStr);

            userRepository.getUserByUserId(userId).ifPresentOrElse(user -> {
                PrincipalUser principal = PrincipalUser.builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .name(user.getName())
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

        } catch (RuntimeException e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token validation error");
            return;
        }

        chain.doFilter(request, response);
    }
}
