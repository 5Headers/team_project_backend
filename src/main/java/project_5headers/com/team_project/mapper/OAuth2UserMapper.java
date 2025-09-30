package project_5headers.com.team_project.mapper;


import org.apache.ibatis.annotations.Mapper;

import project_5headers.com.team_project.entity.OAuth2User;

import java.util.Optional;

@Mapper
public interface OAuth2UserMapper {
    // OAuth2 계정 삭제
    int deleteByUserId(Integer userId);
    // userId로 OAuth2 계정 조회
    OAuth2User getByUserId(Integer userId); // userId로 조회
    Optional<OAuth2User> getOAuth2UserByProviderAndProviderUserId(String provider, String providerUserId);
    int addOAuth2User(OAuth2User oAuth2User);
}