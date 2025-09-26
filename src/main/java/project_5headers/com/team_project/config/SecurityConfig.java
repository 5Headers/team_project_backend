package project_5headers.com.team_project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project_5headers.com.team_project.security.filter.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (JWT 방식에서는 필요 없음)
                .csrf(csrf -> csrf.disable())

                // 세션 사용 안 함 (JWT stateless)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 요청 권한 규칙
                .authorizeHttpRequests(auth -> auth

                        // ✅ maps API 추가 허용
                        .requestMatchers("/auth/**","/oauth2/**", "/chat/**", "/estimate/**", "/account/**", "/api/maps/**").permitAll()
                        // 나머지는 JWT 인증 필요
                        .anyRequest().authenticated()

                        .requestMatchers(
                                "/auth/**",          // 회원가입/로그인
                                "/oauth2/**",        // OAuth2 로그인
                                "/login/**",         // Spring Security 로그인 기본 경로
                                "/error",            // 에러
                                "/api/maps/**"       // 지도 API
                        ).permitAll()
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )

                // OAuth2 로그인 기본 설정 (구글/카카오 등)
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("http://localhost:5173") // ⚡ 로그인 성공 후 리다이렉트
                        .failureUrl("/login?error=true")           // ⚡ 실패 시

                )

                // JWT 필터 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
