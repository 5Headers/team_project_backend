package project_5headers.com.team_project.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.dto.auth.EmailRequestDto;
import project_5headers.com.team_project.service.EmailService;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    //JSON body로 이메일 전송하도록 코드작성함
    @PostMapping("/send")
    public ApiRespDto<?> sendEmail(@RequestBody EmailRequestDto emailRequestDto) {
        return emailService.sendEmailByUsername(
            emailRequestDto.getUsername(),
            emailRequestDto.getSubject(),
            emailRequestDto.getText()
        );
    }
}
