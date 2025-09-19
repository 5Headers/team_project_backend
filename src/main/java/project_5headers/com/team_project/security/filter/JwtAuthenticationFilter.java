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

        // ===== HTTP 메서드 체크 =====
        List<String> methods = List.of("POST", "GET", "PUT", "PATCH", "DELETE");
        if (!methods.contains(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // ===== JWT 검증 =====
        String authorization = request.getHeader("Authorization");

        if (jwtUtils.isBearer(authorization)) {
            String accessToken = jwtUtils.removeBearer(authorization);

            try {
                if (jwtUtils.validateToken(accessToken)) {
                    Claims claims = jwtUtils.getClaims(accessToken);
                    Integer userId = Integer.parseInt(claims.getId());

                    Optional<User> optionalUser = userRepository.getUserByUserId(userId);

                    optionalUser.ifPresentOrElse(user -> {
                        PrincipalUser principalUser = PrincipalUser.builder()
                                .userId(user.getUserId())
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .email(user.getEmail())
                                .userRoles(user.getUserRoles() != null ? user.getUserRoles() : List.of())
                                .build();

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
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
