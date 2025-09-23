package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.dto.account.ResetPasswordReqDto;
import project_5headers.com.team_project.dto.account.ChangePasswordReqDto;
import project_5headers.com.team_project.dto.mail.SendMailReqDto;
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

    @Autowired
    private MailService mailService;



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

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> resetPassword(ResetPasswordReqDto dto) {

        // 이름 + 이메일로 사용자 조회
        Optional<User> optionalUser = userRepository.getUserByNameAndEmail(dto.getName(), dto.getEmail());
        if (optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "사용자가 존재하지 않습니다.", null);
        }

        User user = optionalUser.get();

        // 임시 비밀번호 생성
        String tempPassword = generateTempPassword(); // 랜덤 문자열 생성 메서드
        user.setPassword(bCryptPasswordEncoder.encode(tempPassword));

        // DB 업데이트
        int result = userRepository.changePassword(user);
        if (result != 1) {
            return new ApiRespDto<>("failed", "임시 비밀번호 발급 실패", null);
        }

        // 이메일 전송
        SendMailReqDto mailDto = new SendMailReqDto();
        mailDto.setEmail(user.getEmail());
        mailService.sendTemporaryPassword(mailDto, tempPassword);

        return new ApiRespDto<>("success", "임시 비밀번호가 이메일로 발송되었습니다.", null);
    }

    // 임시 비밀번호 생성 예시
    private String generateTempPassword() {
        // 8~10자리 랜덤 알파벳 + 숫자
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int idx = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }



    // 아이디 찾기 (이름 + 이메일)
    public ApiRespDto<?> findUserIdByNameAndEmail(String name, String email) {
        Optional<User> userOpt = userRepository.getUserByNameAndEmail(name, email);

        if (userOpt.isEmpty()) {
            return new ApiRespDto<>("failed", "일치하는 사용자가 없습니다.", null);
        }

        // 개인정보 보호를 위해 ID만 반환하거나 일부 마스킹 가능
        User user = userOpt.get();
        return new ApiRespDto<>("success", "아이디 조회 성공", user.getUsername());
    }

}