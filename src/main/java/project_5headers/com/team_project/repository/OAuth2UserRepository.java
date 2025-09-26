package project_5headers.com.team_project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project_5headers.com.team_project.entity.OAuth2User;
import project_5headers.com.team_project.mapper.OAuth2UserMapper;

import java.util.Optional;

@Repository
public class OAuth2UserRepository {
    @Autowired
    private OAuth2UserMapper oAuth2UserMapper;

    public Optional<OAuth2User> getOAuth2UserByProviderAndProviderUserId(String provider, String providerUserId) {
        return oAuth2UserMapper.getOAuth2UserByProviderAndProviderUserId(provider, providerUserId);
    }

    public int addOAuth2User(OAuth2User oAuth2User) {
        return oAuth2UserMapper.addOAuth2User(oAuth2User);
    }
}