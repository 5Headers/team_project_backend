package project_5headers.com.team_project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.service.EmailService;
import project_5headers.com.team_project.repository.UserRepository;
import project_5headers.com.team_project.entity.User;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;
    private final UserRepository userRepository;

    public EmailController(EmailService emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    /**
     * 특정 유저에게 이메일 전송
     */
    @PostMapping("/send/{userId}")
    public ResponseEntity<String> sendEmailToUser(
            @PathVariable Integer userId,
            @RequestParam String subject,
            @RequestParam String content) {

        return userRepository.getUserByUserId(userId)
                .map(user -> {
                    emailService.sendEmail(user.getEmail(), subject, content);
                    return ResponseEntity.ok("Email sent to " + user.getEmail());
                })
                .orElseGet(() -> ResponseEntity.badRequest().body("User not found"));
    }
}
