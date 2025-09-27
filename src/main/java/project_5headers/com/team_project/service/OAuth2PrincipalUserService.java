package project_5headers.com.team_project.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OAuth2PrincipalUserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모 클래스(DefaultOAuth2UserService)의 loadUser 실행 → 실제 provider(구글/네이버/카카오)에서 사용자 정보 가져옴
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 가져온 사용자 정보 (attributes) → Map 형식
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 어떤 provider(google/naver/kakao)인지 구분
        String provider = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("provider : " + provider);

        // 공통화할 사용자 정보
        String email = null;
        String id = null;

        // provider 별로 사용자 정보 추출 방식이 다르기 때문에 분기 처리
        switch (provider) {
            case "google":
                // 구글은 "sub" → 고유 ID, "email" → 사용자 이메일
                id = attributes.get("sub").toString();
                email = (String) attributes.get("email");
                break;

            case "naver":
                // 네이버는 response 안에 id, email 등이 들어 있음
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                id = response.get("id").toString();
                email = (String) response.get("email");
                break;

            case "kakao":
                // 카카오는 "id" 가 최상단에 있고, 계정 관련 정보는 "kakao_account" 안에 있음
                id = attributes.get("id").toString();
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                if (kakaoAccount != null && kakaoAccount.containsKey("email")) {
                    // 실제 이메일 꺼내기
                    email = (String) kakaoAccount.get("email");
                } else {
                    // 이메일 동의 안했을 경우 대비
                    email = "unknown@kakao.com";
                }
                break;
        }

        // 최종적으로 공통 포맷으로 가공한 attributes
        Map<String, Object> newAttributes = Map.of(
                "id", id,
                "provider", provider,
                "email", email
        );

        // 로그인한 사용자에게 기본적으로 임시 권한 부여 (회원가입 미완료 상태를 가정)
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_TEMPORARY")
        );

        // 스프링 시큐리티에서 사용할 OAuth2User 반환
        // - authorities : 권한
        // - newAttributes : 사용자 속성(id, email, provider)
        // - "id" : 식별자로 사용할 key
        return new DefaultOAuth2User(authorities, newAttributes, "id");
    }
}