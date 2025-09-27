package project_5headers.com.team_project.mapper;


import org.apache.ibatis.annotations.Mapper;
import project_5headers.com.team_project.entity.OAuth2User;

import java.util.Optional;

@Mapper
public interface OAuth2UserMapper {
    Optional<OAuth2User> getOAuth2UserByProviderAndProviderUserId(String provider, String providerUserId);
    int addOAuth2User(OAuth2User oAuth2User);
}