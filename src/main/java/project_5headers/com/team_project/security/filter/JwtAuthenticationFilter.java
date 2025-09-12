package project_5headers.com.team_project.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.repository.UserRepository;
import project_5headers.com.team_project.security.jwt.JwtUtils;
import project_5headers.com.team_project.security.model.PrincipalUser;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter implements Filter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 필터 적용 HTTP 메서드 제한 (선택사항)
        List<String> methods = List.of("POST", "GET", "PUT", "PATCH", "DELETE");
        if (!methods.contains(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String authorization = request.getHeader("Authorization");

        if (jwtUtils.isBearer(authorization)) {
            String accessToken = jwtUtils.removeBearer(authorization);

            try {
                if (jwtUtils.validateToken(accessToken)) {
                    Claims claims = jwtUtils.getClaims(accessToken);

                    Integer userId = claims.get("userId", Integer.class);

                    Optional<User> optionalUser = userRepository.getUserByUserId(userId);

                    optionalUser.ifPresentOrElse(user -> {
                        // PrincipalUser 생성
                        PrincipalUser principalUser = PrincipalUser.builder()
                                .userId(user.getUserId())
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .email(user.getEmail())
                                .userRoles(user.getUserRoles() != null ? user.getUserRoles() : List.of())
                                .build();

                        // Authentication 객체 생성 및 SecurityContextHolder 등록
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        principalUser,
                                        null,
                                        principalUser.getAuthorities()
                                );

                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }, () -> {
                        throw new AuthenticationServiceException("사용자를 찾을 수 없습니다.");
                    });
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                // 필요 시 인증 실패 처리 가능 (로그, 응답 상태 등)
            }
        }

        // 다음 필터 체인으로 전달
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
