package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.dto.auth.SigninReqDto;
import project_5headers.com.team_project.dto.auth.SignupReqDto;
import project_5headers.com.team_project.entity.Estimate;
import project_5headers.com.team_project.entity.OAuth2User;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.entity.UserRole;
import project_5headers.com.team_project.repository.*;
import project_5headers.com.team_project.security.jwt.JwtUtils;
import project_5headers.com.team_project.security.model.PrincipalUser;

import java.util.List;
import java.util.Optional;

@Service


public class AuthService {

    @Autowired
    private EstimatePartRepository estimatePartRepository;

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private OAuth2UserRepository oauth2UserRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private JwtUtils jwtUtils;


    // 아이디 중복 확인
    public ApiRespDto<?> checkUsername(String username) {
        Optional<User> optionalUserByUsername = userRepository.getUserByUsername(username);
        if (optionalUserByUsername.isPresent()) {
            return new ApiRespDto<>("failed", "이미 사용중인 아이디입니다.", null);
        }
        return new ApiRespDto<>("success", "사용 가능한 아이디입니다.", null);
    }

    // 이메일 중복 확인
    public ApiRespDto<?> checkEmail(String email) {
        Optional<User> optionalUserByEmail = userRepository.getUserByEmail(email);
        if (optionalUserByEmail.isPresent()) {
            return new ApiRespDto<>("failed", "이미 사용중인 이메일입니다.", null);
        }
        return new ApiRespDto<>("success", "사용 가능한 이메일입니다.", null);
    }


    // 회원가입
    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> signup(SignupReqDto signupReqDto) {

        // 아이디 중복 체크
        Optional<User> optionalUserByUsername = userRepository.getUserByUsername(signupReqDto.getUsername());
        if (optionalUserByUsername.isPresent()) {
            return new ApiRespDto<>("failed", "이미 사용중인 아이디입니다.", null);
        }
        ApiRespDto<String> usernameAvailableResp = new ApiRespDto<>("success", "사용 가능한 아이디입니다.", null);


        // 이메일 중복 체크
        Optional<User> optionalUserByEmail = userRepository.getUserByEmail(signupReqDto.getEmail());
        if (optionalUserByEmail.isPresent()) {
            return new ApiRespDto<>("failed", "이미 사용중인 이메일입니다.", null);
        }

        try {
            // 사용자 추가
            Optional<User> optionalUser = userRepository.addUser(signupReqDto.toEntity(bCryptPasswordEncoder));
            if (optionalUser.isEmpty()) {
                throw new RuntimeException("회원정보 추가 실패");
            }

            User user = optionalUser.get();

            // 기본 권한 부여 (ROLE_USER)
            UserRole userRole = UserRole.builder()
                    .userId(user.getUserId())
                    .roleId(3) // 3 = 일반 사용자
                    .build();
            int addUserRoleResult = userRoleRepository.addUserRole(userRole);
            if (addUserRoleResult != 1) {
                throw new RuntimeException("권한 추가 실패");
            }

            return new ApiRespDto<>("success", "회원가입이 완료되었습니다.", user);

        } catch (Exception e) {
            return new ApiRespDto<>("failed", "회원가입 중 오류 발생: " + e.getMessage(), null);
        }
    }

    // 로그인
    public ApiRespDto<?> signin(SigninReqDto signinReqDto) {
        Optional<User> optionalUser = userRepository.getUserByUsername(signinReqDto.getUsername());
        if (optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "아이디 또는 비밀번호가 일치하지 않습니다.", null);
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(signinReqDto.getPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "아이디 또는 비밀번호가 일치하지 않습니다.", null);
        }

        // JWT 토큰 발급
        String accessToken = jwtUtils.generateAccessToken(user.getUserId().toString());
        return new ApiRespDto<>("success", "로그인 성공", accessToken);
    }

    // 회원탈퇴 (견적/부품 + 찜목록 + OAuth2 + 권한 + user_tb)
    @Transactional
    public ApiRespDto<?> withdrawUser(Integer userId) {
        try {
            // 1️⃣ 찜목록 삭제
            bookmarkRepository.getBookmarksByUserId(userId)
                    .forEach(bookmark -> bookmarkRepository.removeBookmarkById(bookmark.getBookmarkId()));

            // 2️⃣ 견적 & 부품 삭제
            List<Estimate> estimates = estimateRepository.getEstimatesByUserId(userId);
            for (Estimate est : estimates) {
                // 견적 부품 삭제
                estimatePartRepository.getPartsByEstimateId(est.getEstimateId())
                        .forEach(part -> estimatePartRepository.removeEstimatePartById(part.getEstimatePartId()));
                // 견적 삭제
                estimateRepository.removeEstimateById(est.getEstimateId());
            }

            // 3️⃣ OAuth2 계정 삭제 (존재할 경우)
            Optional<OAuth2User> oauthUser = oauth2UserRepository.getByUserId(userId);
            oauthUser.ifPresent(u -> oauth2UserRepository.deleteByUserId(userId));

            // 4️⃣ 사용자 권한 삭제
            userRoleRepository.removeRolesByUserId(userId);

            // 5️⃣ user_tb 삭제
            userRepository.removeUserById(userId);

            return new ApiRespDto<>("success", "회원 탈퇴 완료", null);
        } catch (Exception e) {
            return new ApiRespDto<>("failed", "회원 탈퇴 실패: " + e.getMessage(), null);
        }
    }


}