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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();

        // 회원가입/로그인/챗봇 API와 OPTIONS는 인증 제외
        if (path.startsWith("/auth/signup") || path.startsWith("/auth/signin") ||
                "OPTIONS".equalsIgnoreCase(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader("Authorization");
        if (jwtUtils.isBearer(authHeader)) {
            String token = jwtUtils.removeBearer(authHeader);
            try {
                if (jwtUtils.validateToken(token)) {
                    Claims claims = jwtUtils.getClaims(token);

                    // 토큰에서 ID 가져오기
                    String userIdStr = claims.getId(); // AccessToken 생성 시 setId(userId)로 넣었으므로 getId()로 꺼냄
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

                    }, () -> {
                        throw new AuthenticationServiceException("사용자를 찾을 수 없습니다.");
                    });
                }

            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        chain.doFilter(request, response);
    }
}