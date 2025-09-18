package project_5headers.com.team_project.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.repository.UserRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    public ApiRespDto<?> sendEmailByUsername(String username, String subject, String text) {
        Optional<User> userOptional = userRepository.getUserByUsername(username);

        if (userOptional.isEmpty()) {
            return new ApiRespDto<>("failed", "해당 사용자가 존재하지 않습니다.", null);
        }

        String email = userOptional.get().getEmail();

        // 이메일 메시지 구성
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom("my_email@gmail.com");

        // 전송 시도하기
        try {
            javaMailSender.send(simpleMailMessage);
            return new ApiRespDto<>("success", "이메일이 성공적으로 발송되었습니다.", null);
        } catch (MailException e) {
            return new ApiRespDto<>("failed", "이메일 전송 중 오류가 발생하였습니다.", e.getMessage());
        }

    }
}
