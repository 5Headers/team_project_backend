package project_5headers.com.team_project.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project_5headers.com.team_project.repository.UserRepository;
import project_5headers.com.team_project.security.jwt.JwtUtils;
import project_5headers.com.team_project.security.model.PrincipalUser;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("🔎 JwtAuthenticationFilter: 요청 path = " + path);

        // ===== Authorization 헤더 검사 =====
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !jwtUtils.isBearer(authHeader)) {
            // 토큰 필요 없는 엔드포인트는 SecurityConfig에서 permitAll 로 제어함
            chain.doFilter(request, response);
            return;
        }

        String token = jwtUtils.removeBearer(authHeader);

        try {
            if (!jwtUtils.validateToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
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
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token validation error");
            return;
        }

        chain.doFilter(request, response);
    }
}
