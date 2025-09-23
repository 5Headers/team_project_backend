package project_5headers.com.team_project.service;

import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.dto.mail.SendMailReqDto;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.entity.UserRole;
import project_5headers.com.team_project.repository.UserRepository;
import project_5headers.com.team_project.repository.UserRoleRepository;
import project_5headers.com.team_project.security.jwt.JwtUtils;
import project_5headers.com.team_project.security.model.PrincipalUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 인증 메일 발송
     */
    public ApiRespDto<?> sendMail(SendMailReqDto sendMailReqDto, PrincipalUser principalUser) {
        // 본인 이메일 확인
        if (!principalUser.getEmail().equals(sendMailReqDto.getEmail())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        // 이메일 존재 여부 확인
        Optional<User> optionalUser = userRepository.getUserByEmail(sendMailReqDto.getEmail());
        if (optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 이메일입니다.", null);
        }

        User user = optionalUser.get();

        // 임시 사용자(ROLE_TEMPORARY) 여부 확인
        boolean hasTempRole = user.getUserRoles().stream()
                .anyMatch(userRole -> userRole.getRoleId() == 3);
        if (!hasTempRole) {
            return new ApiRespDto<>("failed", "이미 인증 완료된 계정입니다.", null);
        }

        // VerifyToken 생성
        String verifyToken = jwtUtils.generateVerifyToken(user.getUserId().toString());

        // 메일 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("[팀프로젝트] 이메일 인증 안내");
        message.setText("아래 링크를 클릭하여 이메일 인증을 완료해주세요.\n\n"
                + "http://localhost:8080/mail/verify?verifyToken=" + verifyToken);

        // 메일 발송
        javaMailSender.send(message);

        return new ApiRespDto<>("success", "이메일 전송이 완료되었습니다.", null);
    }

    public ApiRespDto<?> sendTemporaryPassword(SendMailReqDto dto, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getEmail());
        message.setSubject("[NuroPC] 임시 비밀번호 발급 안내");
        message.setText("임시 비밀번호: " + tempPassword + "\n로그인 후 반드시 비밀번호를 변경해주세요.");

        javaMailSender.send(message);

        return new ApiRespDto<>("success", "임시 비밀번호 이메일 발송 완료", null);
    }

    /**
     * 인증 링크 검증
     */
    public Map<String, Object> verify(String token) {
        Claims claims;
        try {
            claims = jwtUtils.getClaims(token);

            // VerifyToken인지 확인
            if (!"VerifyToken".equals(claims.getSubject())) {
                return Map.of("status", "failed", "message", "잘못된 요청입니다.");
            }

            Integer userId = Integer.parseInt(claims.getId());

            // 사용자 확인
            Optional<User> optionalUser = userRepository.getUserByUserId(userId);
            if (optionalUser.isEmpty()) {
                return Map.of("status", "failed", "message", "존재하지 않는 사용자입니다.");
            }

            // 아직 ROLE_TEMPORARY 인지 확인
            Optional<UserRole> optionalUserRole = userRoleRepository.getUserRoleByUserIdAndRoleId(userId, 3);
            if (optionalUserRole.isEmpty()) {
                return Map.of("status", "failed", "message", "이미 인증 완료된 계정입니다.");
            }

            // 권한 업데이트 (예: ROLE_USER로 변경)
            userRoleRepository.updateRoleId(optionalUserRole.get().getUserRoleId(), userId);

            return Map.of("status", "success", "message", "이메일 인증이 완료되었습니다.");
        } catch (ExpiredJwtException e) {
            return Map.of("status", "failed", "message", "만료된 인증 요청입니다. 다시 시도해주세요.");
        } catch (Exception e) {
            return Map.of("status", "failed", "message", "잘못된 요청입니다. 인증 메일을 다시 요청하세요.");
        }
    }
}
