package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.dto.auth.SigninReqDto;
import project_5headers.com.team_project.dto.auth.SignupReqDto;
import project_5headers.com.team_project.dto.account.ChangePasswordReqDto;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.repository.UserRepository;
import project_5headers.com.team_project.security.model.PrincipalUser;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    // 비밀번호 변경
    public ApiRespDto<?> changePassword(ChangePasswordReqDto dto, PrincipalUser principalUser) {
        Optional<User> userOpt = userRepository.getUserByUserId(principalUser.getUserId());
        if (userOpt.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 사용자입니다.", null);
        }

        if (!dto.getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        User user = userOpt.get();

        if (!bCryptPasswordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "기존 비밀번호가 일치하지 않습니다.", null);
        }

        if (bCryptPasswordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "새 비밀번호는 기존 비밀번호와 달라야 합니다.", null);
        }

        int result = userRepository.changePassword(dto.toEntity(bCryptPasswordEncoder));
        if (result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다.", null);
        }

        return new ApiRespDto<>("success", "비밀번호 변경이 완료되었습니다. 다시 로그인 해주세요.", null);
    }
}
